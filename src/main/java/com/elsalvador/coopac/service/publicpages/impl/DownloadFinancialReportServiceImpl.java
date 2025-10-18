package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.entity.financial.FinancialReports;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.financial.FinancialReportsRepository;
import com.elsalvador.coopac.service.publicpages.DownloadFinancialReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Implementación del servicio para descargar reportes financieros públicos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadFinancialReportServiceImpl implements DownloadFinancialReportService {

    private final FinancialReportsRepository reportsRepository;

    @Value("${file.storage.type:local}")
    private String storageType;

    @Value("${file.storage.local.base-path:uploads}")
    private String localBasePath;

    @Override
    @Transactional(readOnly = true)
    public Resource downloadReport(UUID reportId) {
        log.info("Solicitada descarga del reporte con ID: {}", reportId);

        FinancialReports report = reportsRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + reportId));

        log.info("Reporte encontrado - ID: {}, Título: '{}', isActive: {}, isPublic: {}",
                reportId, report.getTitle(), report.getIsActive(), report.getIsPublic());

        // Validar que el reporte esté activo y sea público con mensajes específicos
        if (!report.getIsActive()) {
            log.warn("Intento de descarga de reporte inactivo. ID: {}, Slug: {}", reportId, report.getSlug());
            throw new IllegalArgumentException("El reporte '" + report.getTitle() + "' no está activo y no puede ser descargado");
        }

        if (!report.getIsPublic()) {
            log.warn("Intento de descarga de reporte privado. ID: {}, Slug: {}", reportId, report.getSlug());
            throw new IllegalArgumentException("El reporte '" + report.getTitle() + "' es privado y no está disponible para descarga pública");
        }

        try {
            Resource resource;

            if ("local".equalsIgnoreCase(storageType)) {
                // Para almacenamiento local, construir la ruta directamente
                // Estructura: uploads/financial-reports/{reportId}.{extension}
                String extension = report.getFileFormat().toLowerCase();
                String fileName = reportId.toString() + "." + extension;
                Path filePath = Paths.get(localBasePath)
                        .resolve("financial-reports")
                        .resolve(fileName)
                        .normalize();

                log.info("Construyendo ruta local: {}", filePath.toAbsolutePath());

                resource = new UrlResource(filePath.toUri());

                log.info("Resource creado - Existe: {}, Es legible: {}",
                        resource.exists(), resource.isReadable());
            } else {
                // Para GCS, construir la URL directamente
                // Estructura: https://storage.googleapis.com/bucket/financial-reports/{reportId}.{extension}
                String extension = report.getFileFormat().toLowerCase();
                String gcsUrl = report.getFileUrl(); // Usar la URL de GCS desde la BD

                log.info("Usando URL de GCS: {}", gcsUrl);
                resource = new UrlResource(gcsUrl);
            }

            if (resource.exists() && resource.isReadable()) {
                log.info("✅ Archivo encontrado para descarga");
                return resource;
            } else {
                log.error("❌ Archivo no encontrado o no legible");
                throw new RuntimeException("El archivo del reporte no existe o no es accesible");
            }

        } catch (MalformedURLException e) {
            log.error("Error al construir URL del archivo para el reporte ID: {}", reportId, e);
            throw new RuntimeException("Error al acceder al archivo: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public String getFileName(UUID reportId) {
        FinancialReports report = reportsRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + reportId));

        // Validar que el reporte esté activo y sea público con mensajes específicos
        if (!report.getIsActive()) {
            throw new IllegalArgumentException("El reporte '" + report.getTitle() + "' no está activo y no puede ser descargado");
        }

        if (!report.getIsPublic()) {
            throw new IllegalArgumentException("El reporte '" + report.getTitle() + "' es privado y no está disponible para descarga pública");
        }

        // Generar nombre del archivo: slug + extensión
        String extension = report.getFileFormat().toLowerCase();
        return report.getSlug() + "." + extension;
    }
}
