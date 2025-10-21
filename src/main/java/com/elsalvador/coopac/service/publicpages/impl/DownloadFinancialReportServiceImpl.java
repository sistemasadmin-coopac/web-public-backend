package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.entity.financial.FinancialReports;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.financial.FinancialReportsRepository;
import com.elsalvador.coopac.service.publicpages.DownloadFinancialReportService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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

    @Value("${file.storage.gcs.bucket-name:}")
    private String bucketName;

    @Value("${file.storage.gcs.project-id:}")
    private String projectId;

    @Override
    @Transactional(readOnly = true)
    public Resource downloadReport(UUID reportId) {
        log.info("Solicitada descarga del reporte con ID: {}", reportId);

        FinancialReports report = reportsRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + reportId));

        log.info("Reporte encontrado - ID: {}, Título: '{}', isActive: {}, isPublic: {}, Storage: {}",
                reportId, report.getTitle(), report.getIsActive(), report.getIsPublic(), storageType);

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

            if ("gcs".equalsIgnoreCase(storageType)) {
                // Para GCS, descargar el archivo directamente usando el SDK
                String extension = report.getFileFormat().toLowerCase();
                String fileName = reportId + "." + extension;
                String blobName = "financial-reports/" + fileName;

                log.info("📥 Descargando archivo de GCS - Bucket: {}, Path: {}", bucketName, blobName);

                // Usar el SDK de GCS para descargar el archivo
                Storage storage = StorageOptions.newBuilder()
                        .setProjectId(projectId)
                        .build()
                        .getService();

                BlobId blobId = BlobId.of(bucketName, blobName);
                Blob blob = storage.get(blobId);

                if (blob == null || !blob.exists()) {
                    log.error("❌ Archivo no encontrado en GCS: {}/{}", bucketName, blobName);
                    throw new RuntimeException("El archivo del reporte no existe en el almacenamiento");
                }

                // Descargar el contenido del blob
                byte[] content = blob.getContent();
                log.info("✅ Archivo descargado exitosamente de GCS - Tamaño: {} bytes", content.length);

                // Crear un ByteArrayResource con el contenido descargado
                resource = new ByteArrayResource(content) {
                    @Override
                    public String getFilename() {
                        return getFileName(reportId);
                    }
                };

            } else {
                // Para almacenamiento local, construir la ruta directamente
                String extension = report.getFileFormat().toLowerCase();
                String fileName = reportId + "." + extension;
                Path filePath = Paths.get(localBasePath)
                        .resolve("financial-reports")
                        .resolve(fileName)
                        .normalize();

                log.info("Construyendo ruta local: {}", filePath.toAbsolutePath());

                resource = new UrlResource(filePath.toUri());

                if (!resource.exists() || !resource.isReadable()) {
                    log.error("❌ Archivo no encontrado o no legible en almacenamiento local");
                    throw new RuntimeException("El archivo del reporte no existe o no es accesible");
                }

                log.info("✅ Archivo encontrado en almacenamiento local");
            }

            return resource;

        } catch (MalformedURLException e) {
            log.error("Error al construir URL del archivo para el reporte ID: {}", reportId, e);
            throw new RuntimeException("Error al acceder al archivo: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error al descargar archivo del reporte ID: {}", reportId, e);
            throw new RuntimeException("Error al descargar el archivo: " + e.getMessage(), e);
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
