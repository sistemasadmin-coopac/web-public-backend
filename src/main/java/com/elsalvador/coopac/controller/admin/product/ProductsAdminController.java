package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.CreateProductService;
import com.elsalvador.coopac.service.admin.product.DeleteProductService;
import com.elsalvador.coopac.service.admin.product.GetProductsAdminService;
import com.elsalvador.coopac.service.admin.product.UpdateProductService;
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
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Tag(name = SwaggerTags.Products.TAG_NAME, description = SwaggerTags.Products.TAG_DESCRIPTION)
public class ProductsAdminController {

    private final GetProductsAdminService getProductsService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;

    @GetMapping("/complete")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Obtener todos los productos", description = "Obtiene la lista completa de productos con toda su información (características, acciones, badges, pasos, etc.)")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "500", description = "Error")})
    public ResponseEntity<List<ProductsAdminDTO.ProductResponseDTO>> getAllProductsComplete() {
        return ResponseEntity.ok(getProductsService.getAllProductsComplete());
    }

    @PostMapping
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear producto", description = "Crea un nuevo producto con toda su información")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos inválidos")})
    public ResponseEntity<ProductsAdminDTO.ProductResponseDTO> createProduct(@Valid @RequestBody ProductsAdminDTO.CreateProductDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductService.createProduct(createDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Actualizar producto", description = "Actualiza los datos básicos de un producto")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<ProductsAdminDTO.ProductResponseDTO> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductsAdminDTO.UpdateProductDTO updateDTO) {
        return ResponseEntity.ok(updateProductService.updateProduct(id, updateDTO));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Cambiar estado", description = "Activa o desactiva un producto")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<ProductsAdminDTO.ProductResponseDTO> toggleProductStatus(@PathVariable UUID id, @RequestParam Boolean isActive) {
        return ResponseEntity.ok(updateProductService.toggleProductStatus(id, isActive));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Eliminar producto", description = "Elimina un producto completo")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Eliminado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        deleteProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}


