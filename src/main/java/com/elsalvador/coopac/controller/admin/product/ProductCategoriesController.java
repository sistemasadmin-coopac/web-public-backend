package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductCategoriesAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductCategoriesService;
import com.elsalvador.coopac.util.SlugGeneratorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para administración de categorías de productos
 */
@RestController
@RequestMapping("/api/admin/product-categories")
@RequiredArgsConstructor
public class ProductCategoriesController {

    private final ManageProductCategoriesService manageProductCategoriesService;
    private final SlugGeneratorUtil slugGeneratorUtil;

    /**
     * Obtiene todas las categorías
     */
    @GetMapping
    public ResponseEntity<List<ProductCategoriesAdminDTO.ProductCategoryListDTO>> getAllCategories() {
        List<ProductCategoriesAdminDTO.ProductCategoryListDTO> categories =
                manageProductCategoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Obtiene una categoría por ID
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> getCategoryById(
            @PathVariable UUID categoryId) {
        ProductCategoriesAdminDTO.ProductCategoryResponseDTO category =
                manageProductCategoriesService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    /**
     * Crea una nueva categoría
     */
    @PostMapping
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> createCategory(
            @Valid @RequestBody ProductCategoriesAdminDTO.CreateProductCategoryRequestDTO requestDTO) {

        // Generar slug automáticamente a partir del nombre
        String generatedSlug = slugGeneratorUtil.createSlugFromText(requestDTO.name());

        // Crear DTO interno con el slug generado
        ProductCategoriesAdminDTO.CreateProductCategoryDTO createDTO =
                new ProductCategoriesAdminDTO.CreateProductCategoryDTO(
                        requestDTO.name(),
                        generatedSlug,
                        requestDTO.description(),
                        requestDTO.icon(),
                        requestDTO.displayOrder(),
                        requestDTO.isActive()
                );

        ProductCategoriesAdminDTO.ProductCategoryResponseDTO category =
                manageProductCategoriesService.createCategory(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    /**
     * Actualiza una categoría existente
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> updateCategory(
            @PathVariable UUID categoryId,
            @Valid @RequestBody ProductCategoriesAdminDTO.UpdateProductCategoryRequestDTO requestDTO) {

        // Generar slug automáticamente a partir del nombre
        String generatedSlug = slugGeneratorUtil.createSlugFromText(requestDTO.name());

        // Crear DTO interno con el slug generado
        ProductCategoriesAdminDTO.UpdateProductCategoryDTO updateDTO =
                new ProductCategoriesAdminDTO.UpdateProductCategoryDTO(
                        requestDTO.name(),
                        generatedSlug,
                        requestDTO.description(),
                        requestDTO.icon(),
                        requestDTO.displayOrder(),
                        requestDTO.isActive()
                );

        ProductCategoriesAdminDTO.ProductCategoryResponseDTO category =
                manageProductCategoriesService.updateCategory(categoryId, updateDTO);
        return ResponseEntity.ok(category);
    }

    /**
     * Elimina una categoría
     * Valida que no tenga productos asociados antes de eliminar
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        manageProductCategoriesService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Alterna el estado activo/inactivo de una categoría
     */
    @PatchMapping("/{categoryId}/toggle-active")
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> toggleActive(
            @PathVariable UUID categoryId) {
        ProductCategoriesAdminDTO.ProductCategoryResponseDTO category =
                manageProductCategoriesService.toggleActive(categoryId);
        return ResponseEntity.ok(category);
    }
}
