package com.elsalvador.coopac.service.admin.financial.impl;

import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;
import com.elsalvador.coopac.entity.financial.FinancialReportCategories;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.financial.FinancialReportCategoriesRepository;
import com.elsalvador.coopac.repository.financial.FinancialReportsRepository;
import com.elsalvador.coopac.service.admin.financial.ManageFinancialCategoriesService;
import com.elsalvador.coopac.util.SlugGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.FINANCIAL_PAGE_CACHE;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar categorías de reportes financieros
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageFinancialCategoriesServiceImpl implements ManageFinancialCategoriesService {

    private final FinancialReportCategoriesRepository categoriesRepository;
    private final FinancialReportsRepository reportsRepository;
    private final SlugGeneratorUtil slugGeneratorUtil;

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public FinancialAdminDTO.FinancialReportCategoryResponse createCategory(FinancialAdminDTO.FinancialReportCategoryRequest dto) {
        log.info("Creando nueva categoría de reportes: {}", dto.getName());

        // Generar slug automáticamente desde el nombre
        String slug = slugGeneratorUtil.generateUniqueSlug(dto.getName(), categoriesRepository::existsBySlug, "categoria");

        // Calcular automáticamente el siguiente displayOrder
        Integer displayOrder = categoriesRepository.findMaxDisplayOrder() + 1;

        // Crear entidad
        FinancialReportCategories category = FinancialReportCategories.builder()
                .name(dto.getName())
                .slug(slug)
                .description(dto.getDescription())
                .displayOrder(displayOrder)
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();

        FinancialReportCategories savedCategory = categoriesRepository.save(category);
        log.info("Categoría creada exitosamente con ID: {} y slug: {}", savedCategory.getId(), savedCategory.getSlug());

        return mapToResponse(savedCategory);
    }

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public FinancialAdminDTO.FinancialReportCategoryResponse updateCategory(UUID id, FinancialAdminDTO.FinancialReportCategoryRequest dto) {
        log.info("Actualizando categoría con ID: {}", id);

        FinancialReportCategories existingCategory = categoriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        // Si el nombre cambió, regenerar el slug
        if (!existingCategory.getName().equals(dto.getName())) {
            String newSlug = slugGeneratorUtil.generateUniqueSlug(
                dto.getName(),
                slug -> categoriesRepository.existsBySlugAndIdNot(slug, id),
                "categoria"
            );
            existingCategory.setSlug(newSlug);
            existingCategory.setName(dto.getName());
            log.info("Nombre actualizado, nuevo slug generado: {}", newSlug);
        }

        // Actualizar campos
        existingCategory.setDescription(dto.getDescription());
        existingCategory.setIsActive(dto.getIsActive());

        FinancialReportCategories updatedCategory = categoriesRepository.save(existingCategory);
        log.info("Categoría actualizada exitosamente");

        return mapToResponse(updatedCategory);
    }

    @Override
    @Transactional
    @CacheEvict(value = FINANCIAL_PAGE_CACHE, allEntries = true)
    public void deleteCategory(UUID id) {
        log.info("Eliminando categoría con ID: {}", id);

        FinancialReportCategories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        // Verificar si tiene reportes asociados
        Long reportCount = reportsRepository.countByCategory(category);
        if (reportCount > 0) {
            throw new IllegalStateException(
                "No se puede eliminar la categoría porque tiene " + reportCount + " reportes asociados");
        }

        categoriesRepository.deleteById(id);
        log.info("Categoría eliminada exitosamente");
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialAdminDTO.FinancialReportCategoryResponse getCategoryById(UUID id) {
        log.debug("Obteniendo categoría con ID: {}", id);

        FinancialReportCategories category = categoriesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        return mapToResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FinancialAdminDTO.FinancialReportCategoryResponse> getAllCategories() {
        log.debug("Obteniendo todas las categorías");

        return categoriesRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private FinancialAdminDTO.FinancialReportCategoryResponse mapToResponse(FinancialReportCategories category) {
        return FinancialAdminDTO.FinancialReportCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .displayOrder(category.getDisplayOrder())
                .isActive(category.getIsActive())
                .build();
    }
}
