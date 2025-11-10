package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductCategoriesAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductCategoriesService;
import com.elsalvador.coopac.util.SlugGeneratorUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product-categories")
@RequiredArgsConstructor
@Tag(name = SwaggerTags.Products.TAG_NAME, description = SwaggerTags.Products.TAG_DESCRIPTION)
public class ProductCategoriesController {

    private final ManageProductCategoriesService manageProductCategoriesService;
    private final SlugGeneratorUtil slugGeneratorUtil;

    @GetMapping
    @Operation(summary = SwaggerTags.Products.EMOJI_CATEGORIES + " Obtener todas las categorías", description = "Obtiene la lista completa de categorías de productos")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "500", description = "Error")})
    public ResponseEntity<List<ProductCategoriesAdminDTO.ProductCategoryListDTO>> getAllCategories() {
        return ResponseEntity.ok(manageProductCategoriesService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_CATEGORIES + " Obtener categoría por ID", description = "Obtiene los detalles de una categoría específica")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> getCategoryById(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(manageProductCategoriesService.getCategoryById(categoryId));
    }

    @PostMapping
    @Operation(summary = SwaggerTags.Products.EMOJI_CATEGORIES + " Crear categoría", description = "Crea una nueva categoría de productos")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos inválidos")})
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> createCategory(@Valid @RequestBody ProductCategoriesAdminDTO.CreateProductCategoryRequestDTO requestDTO) {
        String generatedSlug = slugGeneratorUtil.createSlugFromText(requestDTO.name());
        ProductCategoriesAdminDTO.CreateProductCategoryDTO createDTO = new ProductCategoriesAdminDTO.CreateProductCategoryDTO(
                requestDTO.name(), generatedSlug, requestDTO.description(), requestDTO.icon(), requestDTO.displayOrder(), requestDTO.isActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductCategoriesService.createCategory(createDTO));
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_CATEGORIES + " Actualizar categoría", description = "Actualiza una categoría existente")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> updateCategory(@PathVariable UUID categoryId, @Valid @RequestBody ProductCategoriesAdminDTO.UpdateProductCategoryRequestDTO requestDTO) {
        String generatedSlug = slugGeneratorUtil.createSlugFromText(requestDTO.name());
        ProductCategoriesAdminDTO.UpdateProductCategoryDTO updateDTO = new ProductCategoriesAdminDTO.UpdateProductCategoryDTO(
                requestDTO.name(), generatedSlug, requestDTO.description(), requestDTO.icon(), requestDTO.displayOrder(), requestDTO.isActive());
        return ResponseEntity.ok(manageProductCategoriesService.updateCategory(categoryId, updateDTO));
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_CATEGORIES + " Eliminar categoría", description = "Elimina una categoría (valida que no tenga productos)")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Eliminado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        manageProductCategoriesService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{categoryId}/toggle-active")
    @Operation(summary = SwaggerTags.Products.EMOJI_CATEGORIES + " Cambiar estado", description = "Activa o desactiva una categoría")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<ProductCategoriesAdminDTO.ProductCategoryResponseDTO> toggleActive(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(manageProductCategoriesService.toggleActive(categoryId));
    }
}


