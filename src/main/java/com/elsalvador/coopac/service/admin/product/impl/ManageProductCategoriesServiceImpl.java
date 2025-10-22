package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductCategoriesAdminDTO;
import com.elsalvador.coopac.entity.product.ProductCategories;
import com.elsalvador.coopac.exception.BusinessValidationException;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductCategoriesRepository;
import com.elsalvador.coopac.service.admin.product.ManageProductCategoriesService;
import com.elsalvador.coopac.util.SlugGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementación del servicio para gestionar categorías de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageProductCategoriesServiceImpl implements ManageProductCategoriesService {

    private final ProductCategoriesRepository productCategoriesRepository;
    private final SlugGeneratorUtil slugGeneratorUtil;

    @Override
    @Transactional(readOnly = true)
    public List<ProductCategoriesAdminDTO.ProductCategoryListDTO> getAllCategories() {
        log.debug("Obteniendo todas las categorías de productos");

        List<ProductCategories> categories = productCategoriesRepository.findAllOrderByDisplayOrder();

        return categories.stream()
                .map(this::mapToListDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductCategoriesAdminDTO.ProductCategoryResponseDTO getCategoryById(UUID categoryId) {
        log.debug("Obteniendo categoría con ID: {}", categoryId);

        ProductCategories category = findCategoryById(categoryId);
        return mapToResponseDTO(category);
    }

    @Override
    public ProductCategoriesAdminDTO.ProductCategoryResponseDTO createCategory(
            ProductCategoriesAdminDTO.CreateProductCategoryDTO createDTO) {
        log.debug("Creando nueva categoría con nombre: {}", createDTO.name());

        // Validar que el nombre no exista
        validateNameNotExists(createDTO.name());

        // Validar que el slug sea único y generar uno único si es necesario
        String finalSlug = generateUniqueSlugFromProvided(createDTO.slug(), null);

        // Asignar displayOrder por defecto si el request no lo provee
        Integer displayOrderToUse = createDTO.displayOrder();
        if (displayOrderToUse == null) {
            Integer maxOrder = productCategoriesRepository.findMaxDisplayOrder();
            displayOrderToUse = (maxOrder != null) ? (maxOrder + 1) : 1;
            log.debug("displayOrder no proporcionado, asignando por defecto: {}", displayOrderToUse);
        }

        ProductCategories category = ProductCategories.builder()
                .name(createDTO.name())
                .slug(finalSlug)
                .description(createDTO.description())
                .icon(createDTO.icon())
                .displayOrder(displayOrderToUse)
                .isActive(createDTO.isActive())
                .build();

        ProductCategories savedCategory = productCategoriesRepository.save(category);
        log.info("Categoría creada exitosamente con ID: {} y slug: {}", savedCategory.getId(), savedCategory.getSlug());

        return mapToResponseDTO(savedCategory);
    }

    @Override
    public ProductCategoriesAdminDTO.ProductCategoryResponseDTO updateCategory(
            UUID categoryId, ProductCategoriesAdminDTO.UpdateProductCategoryDTO updateDTO) {
        log.debug("Actualizando categoría con ID: {}", categoryId);

        ProductCategories category = findCategoryById(categoryId);

        // Validar que el nombre no exista en otra categoría
        validateNameNotExistsForUpdate(updateDTO.name(), categoryId);

        // Validar que el slug sea único y generar uno único si es necesario
        String finalSlug = generateUniqueSlugFromProvided(updateDTO.slug(), categoryId);

        category.setName(updateDTO.name());
        category.setSlug(finalSlug);
        category.setDescription(updateDTO.description());
        category.setIcon(updateDTO.icon());

        // Mantener el displayOrder actual si no se proporciona uno nuevo
        if (updateDTO.displayOrder() != null) {
            category.setDisplayOrder(updateDTO.displayOrder());
            log.debug("displayOrder actualizado a: {}", updateDTO.displayOrder());
        } else {
            log.debug("displayOrder no proporcionado, se mantiene el actual: {}", category.getDisplayOrder());
        }

        category.setIsActive(updateDTO.isActive());

        ProductCategories savedCategory = productCategoriesRepository.save(category);
        log.info("Categoría actualizada exitosamente con ID: {} y slug: {}", savedCategory.getId(), savedCategory.getSlug());

        return mapToResponseDTO(savedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        log.debug("Eliminando categoría con ID: {}", categoryId);

        ProductCategories category = findCategoryById(categoryId);

        // Validar que no tenga productos asociados
        validateNoAssociatedProducts(categoryId);

        productCategoriesRepository.delete(category);
        log.info("Categoría eliminada exitosamente con ID: {}", categoryId);
    }

    /**
     * Busca una categoría por ID, lanza excepción si no existe
     */
    private ProductCategories findCategoryById(UUID categoryId) {
        return productCategoriesRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con ID: " + categoryId));
    }

    /**
     * Valida que el slug no exista
     */
    private void validateSlugNotExists(String slug) {
        if (productCategoriesRepository.existsBySlug(slug)) {
            throw new BusinessValidationException(
                    "slug", slug,
                    "Ya existe una categoría con el slug: " + slug);
        }
    }

    /**
     * Valida que el slug no exista en otra categoría (para actualización)
     */
    private void validateSlugNotExistsForUpdate(String slug, UUID excludeId) {
        if (productCategoriesRepository.existsBySlugAndIdNot(slug, excludeId)) {
            throw new BusinessValidationException(
                    "slug", slug,
                    "Ya existe otra categoría con el slug: " + slug);
        }
    }

    /**
     * Valida que la categoría no tenga productos asociados
     */
    private void validateNoAssociatedProducts(UUID categoryId) {
        if (productCategoriesRepository.hasAssociatedProducts(categoryId)) {
            long productsCount = productCategoriesRepository.countProductsByCategoryId(categoryId);
            throw new BusinessValidationException(
                    "No se puede eliminar la categoría porque tiene " + productsCount +
                    " producto(s) asociado(s). Primero debe reasignar o eliminar los productos.");
        }
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private ProductCategoriesAdminDTO.ProductCategoryResponseDTO mapToResponseDTO(ProductCategories category) {
        long productsCount = productCategoriesRepository.countProductsByCategoryId(category.getId());

        return new ProductCategoriesAdminDTO.ProductCategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getIcon(),
                category.getDisplayOrder(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                productsCount
        );
    }

    /**
     * Mapea entidad a DTO de lista
     */
    private ProductCategoriesAdminDTO.ProductCategoryListDTO mapToListDTO(ProductCategories category) {
        long productsCount = productCategoriesRepository.countProductsByCategoryId(category.getId());

        return new ProductCategoriesAdminDTO.ProductCategoryListDTO(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getIcon(),
                category.getDisplayOrder(),
                category.getIsActive(),
                productsCount
        );
    }

    /**
     * Valida que el nombre no exista
     */
    private void validateNameNotExists(String name) {
        if (productCategoriesRepository.existsByName(name)) {
            throw new BusinessValidationException(
                    "name", name,
                    "Ya existe una categoría con el nombre: " + name);
        }
    }

    /**
     * Valida que el nombre no exista en otra categoría (para actualización)
     */
    private void validateNameNotExistsForUpdate(String name, UUID excludeId) {
        if (productCategoriesRepository.existsByNameAndIdNot(name, excludeId)) {
            throw new BusinessValidationException(
                    "name", name,
                    "Ya existe otra categoría con el nombre: " + name);
        }
    }

    /**
     * Genera un slug único a partir del slug proporcionado
     */
    private String generateUniqueSlugFromProvided(String providedSlug, UUID excludeId) {
        return slugGeneratorUtil.generateUniqueSlug(
            providedSlug,
            slug -> excludeId != null
                ? productCategoriesRepository.existsBySlugAndIdNot(slug, excludeId)
                : productCategoriesRepository.existsBySlug(slug),
            "categoria"
        );
    }
}
