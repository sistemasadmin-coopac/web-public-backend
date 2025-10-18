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
    public FinancialAdminDTO.FinancialReportResponse createReport(FinancialAdminDTO.FinancialReportRequest dto) {
        log.info("Creando nuevo reporte financiero: {}", dto.getTitle());

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

        // Crear entidad
        FinancialReports report = FinancialReports.builder()
                .category(category)
                .slug(slug)
                .title(dto.getTitle())
                .summary(dto.getSummary())
                .year(dto.getYear())
                .quarter(dto.getQuarter())
                .publishDate(dto.getPublishDate())
                .deliveryType(dto.getDeliveryType())
                .fileFormat(dto.getFileFormat())
                .fileUrl(dto.getFileUrl())
                .fileSizeBytes(dto.getFileSizeBytes())
                .thumbnailUrl(dto.getThumbnailUrl())
                .tags(dto.getTags())
                .isPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true)
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .displayOrder(displayOrder)
                .build();

        FinancialReports savedReport = reportsRepository.save(report);
        log.info("Reporte financiero creado exitosamente con ID: {} y slug: {}", savedReport.getId(), savedReport.getSlug());

        return mapToResponse(savedReport);
    }

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public FinancialAdminDTO.FinancialReportResponse updateReport(UUID id, FinancialAdminDTO.FinancialReportUpdateRequest dto) {
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

        // Actualizar campos
        existingReport.setSummary(dto.getSummary());
        existingReport.setYear(dto.getYear());
        existingReport.setQuarter(dto.getQuarter());
        existingReport.setPublishDate(dto.getPublishDate());
        existingReport.setDeliveryType(dto.getDeliveryType());

        // Actualizar fileFormat solo si se proporciona
        if (dto.getFileFormat() != null && !dto.getFileFormat().isEmpty()) {
            existingReport.setFileFormat(dto.getFileFormat());
        }

        // Si la URL del archivo cambió, eliminar el anterior si es local
        if (dto.getFileUrl() != null && !dto.getFileUrl().isEmpty()
                && !existingReport.getFileUrl().equals(dto.getFileUrl())) {
            try {
                if (existingReport.getDeliveryType().equals("file")) {
                    fileStorageService.deleteFile(existingReport.getFileUrl());
                }
            } catch (Exception e) {
                log.warn("Error al eliminar archivo anterior: {}", e.getMessage());
            }
            existingReport.setFileUrl(dto.getFileUrl());
            log.info("URL del archivo actualizada");
        }
        // Si no se envió fileUrl o está vacío, mantener la existente

        // Actualizar fileSizeBytes solo si se proporcionó
        if (dto.getFileSizeBytes() != null) {
            existingReport.setFileSizeBytes(dto.getFileSizeBytes());
        }

        // Si la miniatura cambió, eliminar la anterior
        if (dto.getThumbnailUrl() != null && !dto.getThumbnailUrl().isEmpty()) {
            if (!dto.getThumbnailUrl().equals(existingReport.getThumbnailUrl())) {
                try {
                    if (existingReport.getThumbnailUrl() != null && !existingReport.getThumbnailUrl().isEmpty()) {
                        fileStorageService.deleteFile(existingReport.getThumbnailUrl());
                    }
                } catch (Exception e) {
                    log.warn("Error al eliminar miniatura anterior: {}", e.getMessage());
                }
                existingReport.setThumbnailUrl(dto.getThumbnailUrl());
                log.info("Miniatura actualizada");
            }
        }
        // Si no se envió thumbnailUrl o está vacío, mantener la existente

        existingReport.setTags(dto.getTags());
        existingReport.setIsPublic(dto.getIsPublic());
        existingReport.setIsActive(dto.getIsActive());
        existingReport.setDisplayOrder(dto.getDisplayOrder());

        FinancialReports updatedReport = reportsRepository.save(existingReport);
        log.info("Reporte financiero actualizado exitosamente");

        return mapToResponse(updatedReport);
    }

    @Override
    @Transactional
    public void deleteReport(UUID id) {
        log.info("Eliminando reporte financiero con ID: {}", id);

        FinancialReports report = reportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con ID: " + id));

        // Eliminar archivos asociados si son locales
        try {
            if (report.getDeliveryType().equals("file")) {
                fileStorageService.deleteFile(report.getFileUrl());
            }
            if (report.getThumbnailUrl() != null) {
                fileStorageService.deleteFile(report.getThumbnailUrl());
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

        return reportsRepository.findAllByOrderByDisplayOrderAscYearDescPublishDateDesc()
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

        return reportsRepository.findByCategoryOrderByDisplayOrderAscYearDescPublishDateDesc(category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FinancialAdminDTO.FileUploadResponse uploadReportFile(MultipartFile file) {
        log.info("Subiendo archivo de reporte: {}", file.getOriginalFilename());

        validateFile(file);

        String fileUrl = fileStorageService.storeFile(file, "financial-reports");
        String fileFormat = getFileExtension(file.getOriginalFilename());

        return FinancialAdminDTO.FileUploadResponse.builder()
                .fileUrl(fileUrl)
                .fileName(file.getOriginalFilename())
                .fileSizeBytes(file.getSize())
                .fileFormat(fileFormat)
                .build();
    }

    @Override
    public FinancialAdminDTO.FileUploadResponse uploadThumbnail(MultipartFile file) {
        log.info("Subiendo miniatura: {}", file.getOriginalFilename());

        validateImageFile(file);

        String fileUrl = fileStorageService.storeFile(file, "financial-reports/thumbnails");
        String fileFormat = getFileExtension(file.getOriginalFilename());

        return FinancialAdminDTO.FileUploadResponse.builder()
                .fileUrl(fileUrl)
                .fileName(file.getOriginalFilename())
                .fileSizeBytes(file.getSize())
                .fileFormat(fileFormat)
                .build();
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Validar tamaño (50MB máximo)
        if (file.getSize() > 50 * 1024 * 1024) {
            throw new IllegalArgumentException("El archivo no puede superar los 50MB");
        }

        // Validar tipo de archivo
        String filename = file.getOriginalFilename();
        if (filename == null || !isValidFileExtension(filename)) {
            throw new IllegalArgumentException("Formato de archivo no permitido. Solo se permiten: PDF, XLS, XLSX, DOC, DOCX");
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

    private boolean isValidFileExtension(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return extension.equals("pdf") || extension.equals("xls") || extension.equals("xlsx")
                || extension.equals("doc") || extension.equals("docx");
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    private FinancialAdminDTO.FinancialReportResponse mapToResponse(FinancialReports report) {
        return FinancialAdminDTO.FinancialReportResponse.builder()
                .id(report.getId())
                .categoryId(report.getCategory().getId())
                .categoryName(report.getCategory().getName())
                .slug(report.getSlug())
                .title(report.getTitle())
                .summary(report.getSummary())
                .year(report.getYear())
                .quarter(report.getQuarter())
                .publishDate(report.getPublishDate())
                .deliveryType(report.getDeliveryType())
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
