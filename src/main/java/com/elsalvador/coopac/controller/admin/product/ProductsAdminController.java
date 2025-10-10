package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.CreateProductService;
import com.elsalvador.coopac.service.admin.product.DeleteProductService;
import com.elsalvador.coopac.service.admin.product.GetProductsAdminService;
import com.elsalvador.coopac.service.admin.product.UpdateProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para administración de productos
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductsAdminController {

    private final GetProductsAdminService getProductsService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;

    /**
     * Obtiene todos los productos con toda su data completa para administración
     */
    @GetMapping("/complete")
    public ResponseEntity<List<ProductsAdminDTO.ProductResponseDTO>> getAllProductsComplete() {
        List<ProductsAdminDTO.ProductResponseDTO> products = getProductsService.getAllProductsComplete();
        return ResponseEntity.ok(products);
    }

    /**
     * Crea un nuevo producto
     */
    @PostMapping
    public ResponseEntity<ProductsAdminDTO.ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductsAdminDTO.CreateProductDTO createDTO) {
        ProductsAdminDTO.ProductResponseDTO product = createProductService.createProduct(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Actualiza un producto
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductsAdminDTO.ProductResponseDTO> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductsAdminDTO.UpdateProductDTO updateDTO) {
        ProductsAdminDTO.ProductResponseDTO product = updateProductService.updateProduct(id, updateDTO);
        return ResponseEntity.ok(product);
    }

    /**
     * Activa o desactiva un producto
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProductsAdminDTO.ProductResponseDTO> toggleProductStatus(
            @PathVariable UUID id,
            @RequestParam Boolean isActive) {
        ProductsAdminDTO.ProductResponseDTO product = updateProductService.toggleProductStatus(id, isActive);
        return ResponseEntity.ok(product);
    }

    /**
     * Elimina un producto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        deleteProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
