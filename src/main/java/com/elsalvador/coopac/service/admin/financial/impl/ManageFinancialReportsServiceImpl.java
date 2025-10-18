package com.elsalvador.coopac.service.admin.financial.impl;

import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;
import com.elsalvador.coopac.entity.financial.FinancialReportCategories;
import com.elsalvador.coopac.entity.financial.FinancialReports;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.financial.FinancialReportCategoriesRepository;
import com.elsalvador.coopac.repository.financial.FinancialReportsRepository;
import com.elsalvador.coopac.service.admin.financial.ManageFinancialReportsService;
import com.elsalvador.coopac.service.storage.FileStorageService;
import com.elsalvador.coopac.util.SlugGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.elsalvador.coopac.config.CacheConfig.FINANCIAL_PAGE_CACHE;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar reportes financieros
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageFinancialReportsServiceImpl implements ManageFinancialReportsService {

    private final FinancialReportsRepository reportsRepository;
    private final FinancialReportCategoriesRepository categoriesRepository;
    private final FileStorageService fileStorageService;
    private final SlugGeneratorUtil slugGeneratorUtil;

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public FinancialAdminDTO.FinancialReportResponse createReport(
            FinancialAdminDTO.FinancialReportRequest dto,
            MultipartFile file,
            MultipartFile thumbnail) {

        log.info("Creando nuevo reporte financiero: {}", dto.getTitle());

        // Validar que se proporcionó un archivo
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar un archivo");
        }
        validateFile(file);

        // Validar que la categoría existe
        FinancialReportCategories category = categoriesRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + dto.getCategoryId()));

        // Generar slug automáticamente desde el título
        String slug = slugGeneratorUtil.generateUniqueSlug(dto.getTitle(), reportsRepository::existsBySlug, "reporte");

        // Obtener el siguiente displayOrder si no se especifica
        Integer displayOrder = dto.getDisplayOrder();
        if (displayOrder == null || displayOrder == 0) {
            displayOrder = reportsRepository.findMaxDisplayOrder() + 1;
        }

        // Calcular fileFormat usando MIME type
        String fileFormat = getFileExtensionFromMimeType(file.getContentType(), file.getOriginalFilename());

        // Crear entidad con fileFormat y publishDate se genera automáticamente
        FinancialReports report = FinancialReports.builder()
                .category(category)
                .slug(slug)
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .publishDate(java.time.LocalDate.now()) // Fecha automática
                .fileFormat(fileFormat)
                .fileUrl("") // Temporal, se actualiza después
                .fileSizeBytes(null)
                .thumbnailUrl(null)
                .tags(dto.getTags())
                .isPublic(dto.getIsPublic())
                .isActive(dto.getIsActive())
                .displayOrder(displayOrder)
                .build();

        // Guardar para obtener el ID generado
        FinancialReports savedReport = reportsRepository.save(report);
        log.info("Reporte creado con ID: {}", savedReport.getId());

        // Subir archivo usando el ID del reporte como nombre
        String fileUrl = fileStorageService.storeFileWithName(file, "financial-reports", savedReport.getId().toString());
        Long fileSizeBytes = file.getSize();
        log.info("Archivo subido con nombre: {} -> {}", savedReport.getId(), fileUrl);

        // Subir miniatura si se proporciona
        String thumbnailUrl = null;
        if (thumbnail != null && !thumbnail.isEmpty()) {
            validateImageFile(thumbnail);
            thumbnailUrl = fileStorageService.storeFileWithName(thumbnail, "financial-reports/thumbnails", savedReport.getId().toString());
            log.info("Miniatura subida con nombre: {} -> {}", savedReport.getId(), thumbnailUrl);
        }

        // Actualizar el reporte con las URLs de los archivos
        savedReport.setFileUrl(fileUrl);
        savedReport.setFileSizeBytes(fileSizeBytes);
        savedReport.setThumbnailUrl(thumbnailUrl);

        FinancialReports finalReport = reportsRepository.save(savedReport);
        log.info("Reporte financiero completado exitosamente con ID: {} y slug: {}", finalReport.getId(), finalReport.getSlug());

        return mapToResponse(finalReport);
    }

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public FinancialAdminDTO.FinancialReportResponse updateReport(
            UUID id,
            FinancialAdminDTO.FinancialReportUpdateRequest dto,
            MultipartFile file,
            MultipartFile thumbnail) {

        log.info("Actualizando reporte financiero con ID: {}", id);

        FinancialReports existingReport = reportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));

        // Validar categoría si cambió
        if (!existingReport.getCategory().getId().equals(dto.getCategoryId())) {
            FinancialReportCategories category = categoriesRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + dto.getCategoryId()));
            existingReport.setCategory(category);
        }

        // Si el título cambió, regenerar el slug
        if (!existingReport.getTitle().equals(dto.getTitle())) {
            String newSlug = slugGeneratorUtil.generateUniqueSlug(
                dto.getTitle(),
                slug -> reportsRepository.existsBySlugAndIdNot(slug, id),
                "reporte"
            );
            existingReport.setSlug(newSlug);
            existingReport.setTitle(dto.getTitle());
            log.info("Título actualizado, nuevo slug generado: {}", newSlug);
        }

        // Actualizar campos básicos
        existingReport.setSummary(dto.getSummary());
        existingReport.setTags(dto.getTags());
        existingReport.setIsPublic(dto.getIsPublic());
        existingReport.setIsActive(dto.getIsActive());
        existingReport.setDisplayOrder(dto.getDisplayOrder());

        // Procesar archivo principal si se proporciona uno nuevo
        if (file != null && !file.isEmpty()) {
            // Eliminar archivo anterior si existe
            if (existingReport.getFileUrl() != null && !existingReport.getFileUrl().isEmpty()) {
                try {
                    fileStorageService.deleteFile(existingReport.getFileUrl());
                    log.info("Archivo anterior eliminado: {}", existingReport.getFileUrl());
                } catch (Exception e) {
                    log.warn("Error al eliminar archivo anterior: {}", e.getMessage());
                }
            }

            // Subir nuevo archivo usando el ID del reporte como nombre
            validateFile(file);
            String newFileUrl = fileStorageService.storeFileWithName(file, "financial-reports", id.toString());
            String fileFormat = getFileExtensionFromMimeType(file.getContentType(), file.getOriginalFilename());
            existingReport.setFileUrl(newFileUrl);
            existingReport.setFileFormat(fileFormat);
            existingReport.setFileSizeBytes(file.getSize());
            log.info("Nuevo archivo subido: {}", newFileUrl);
        }

        // Procesar miniatura si se proporciona una nueva
        if (thumbnail != null && !thumbnail.isEmpty()) {
            // Eliminar miniatura anterior si existe
            if (existingReport.getThumbnailUrl() != null && !existingReport.getThumbnailUrl().isEmpty()) {
                try {
                    fileStorageService.deleteFile(existingReport.getThumbnailUrl());
                    log.info("Miniatura anterior eliminada: {}", existingReport.getThumbnailUrl());
                } catch (Exception e) {
                    log.warn("Error al eliminar miniatura anterior: {}", e.getMessage());
                }
            }

            // Subir nueva miniatura usando el ID del reporte como nombre
            validateImageFile(thumbnail);
            String newThumbnailUrl = fileStorageService.storeFileWithName(thumbnail, "financial-reports/thumbnails", id.toString());
            existingReport.setThumbnailUrl(newThumbnailUrl);
            log.info("Nueva miniatura subida: {}", newThumbnailUrl);
        }

        FinancialReports updatedReport = reportsRepository.save(existingReport);
        log.info("Reporte financiero actualizado exitosamente");

        return mapToResponse(updatedReport);
    }

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public void deleteReport(UUID id) {
        log.info("Eliminando reporte financiero con ID: {}", id);

        FinancialReports report = reportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));

        // Eliminar archivos asociados
        try {
            if (report.getFileUrl() != null && !report.getFileUrl().isEmpty()) {
                fileStorageService.deleteFile(report.getFileUrl());
                log.info("Archivo eliminado: {}", report.getFileUrl());
            }
            if (report.getThumbnailUrl() != null && !report.getThumbnailUrl().isEmpty()) {
                fileStorageService.deleteFile(report.getThumbnailUrl());
                log.info("Miniatura eliminada: {}", report.getThumbnailUrl());
            }
        } catch (Exception e) {
            log.warn("Error al eliminar archivos: {}", e.getMessage());
        }

        reportsRepository.deleteById(id);
        log.info("Reporte financiero eliminado exitosamente");
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialAdminDTO.FinancialReportResponse getReportById(UUID id) {
        log.debug("Obteniendo reporte financiero con ID: {}", id);

        FinancialReports report = reportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));

        return mapToResponse(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FinancialAdminDTO.FinancialReportResponse> getAllReports() {
        log.debug("Obteniendo todos los reportes financieros");

        return reportsRepository.findAllByOrderByDisplayOrderAscPublishDateDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FinancialAdminDTO.FinancialReportResponse> getReportsByCategory(UUID categoryId) {
        log.debug("Obteniendo reportes por categoría: {}", categoryId);

        FinancialReportCategories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + categoryId));

        return reportsRepository.findByCategoryOrderByDisplayOrderAscPublishDateDesc(category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Validar tamaño (50MB máximo)
        if (file.getSize() > 50 * 1024 * 1024) {
            throw new IllegalArgumentException("El archivo no puede superar los 50MB");
        }

        // Validar tipo de archivo usando MIME type
        String contentType = file.getContentType();
        if (contentType == null || !isValidMimeType(contentType)) {
            throw new IllegalArgumentException("Formato de archivo no permitido. Solo se permiten: PDF, Excel (XLS, XLSX), Word (DOC, DOCX)");
        }
    }

    private void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("La imagen está vacía");
        }

        // Validar tamaño (5MB máximo)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("La imagen no puede superar los 5MB");
        }

        // Validar tipo de imagen
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }
    }

    private boolean isValidMimeType(String mimeType) {
        return mimeType.equals("application/pdf") ||
               mimeType.equals("application/vnd.ms-excel") ||
               mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
               mimeType.equals("application/msword") ||
               mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    /**
     * Valida la extensión del archivo basándose en el nombre.
     * Solo permite: PDF, XLS, XLSX, DOC, DOCX
     */
    private boolean isValidFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return false;
        }
        String extension = getFileExtension(filename).toLowerCase();
        return extension.equals("pdf") ||
               extension.equals("xls") ||
               extension.equals("xlsx") ||
               extension.equals("doc") ||
               extension.equals("docx");
    }

    /**
     * Extrae la extensión del archivo desde el nombre.
     * Maneja correctamente nombres con múltiples puntos (ej: archivo.reporte.2024.pdf)
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * Obtiene la extensión del archivo basándose en el MIME type.
     * Es más confiable que usar el nombre del archivo.
     * Retorna el formato en MAYÚSCULAS para cumplir con la restricción CHECK de la base de datos.
     */
    private String getFileExtensionFromMimeType(String mimeType, String fallbackFilename) {
        if (mimeType == null) {
            // Fallback: intentar extraer del nombre del archivo
            return getFileExtension(fallbackFilename).toUpperCase();
        }

        // Mapear MIME types a extensiones en MAYÚSCULAS
        switch (mimeType) {
            case "application/pdf":
                return "PDF";
            case "application/vnd.ms-excel":
                return "XLS";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                return "XLSX";
            case "application/msword":
                return "DOC";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return "DOCX";
            default:
                // Si no reconoce el MIME type, intenta extraer del nombre
                return getFileExtension(fallbackFilename).toUpperCase();
        }
    }

    private FinancialAdminDTO.FinancialReportResponse mapToResponse(FinancialReports report) {
        return FinancialAdminDTO.FinancialReportResponse.builder()
                .id(report.getId())
                .categoryId(report.getCategory().getId())
                .categoryName(report.getCategory().getName())
                .slug(report.getSlug())
                .title(report.getTitle())
                .summary(report.getSummary())
                .publishDate(report.getPublishDate())
                .fileFormat(report.getFileFormat())
                .fileUrl(report.getFileUrl())
                .fileSizeBytes(report.getFileSizeBytes())
                .thumbnailUrl(report.getThumbnailUrl())
                .tags(report.getTags())
                .isPublic(report.getIsPublic())
                .isActive(report.getIsActive())
                .displayOrder(report.getDisplayOrder())
                .build();
    }
}
