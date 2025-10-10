package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductFeaturesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para administración de características de productos
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductFeaturesController {

    private final ManageProductFeaturesService manageProductFeaturesService;

    /**
     * Añade una característica a un producto
     */
    @PostMapping("/{productId}/features")
    public ResponseEntity<ProductsAdminDTO.ProductFeatureDTO> addFeature(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductsAdminDTO.CreateProductFeatureDTO createDTO) {
        ProductsAdminDTO.ProductFeatureDTO feature =
                manageProductFeaturesService.addFeature(productId, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(feature);
    }

    /**
     * Actualiza una característica
     */
    @PutMapping("/{productId}/features/{featureId}")
    public ResponseEntity<ProductsAdminDTO.ProductFeatureDTO> updateFeature(
            @PathVariable UUID productId,
            @PathVariable UUID featureId,
            @Valid @RequestBody ProductsAdminDTO.UpdateProductFeatureDTO updateDTO) {
        ProductsAdminDTO.ProductFeatureDTO feature =
                manageProductFeaturesService.updateFeature(featureId, updateDTO);
        return ResponseEntity.ok(feature);
    }

    /**
     * Elimina una característica
     */
    @DeleteMapping("/{productId}/features/{featureId}")
    public ResponseEntity<Void> deleteFeature(
            @PathVariable UUID productId,
            @PathVariable UUID featureId) {
        manageProductFeaturesService.deleteFeature(featureId);
        return ResponseEntity.noContent().build();
    }
}
