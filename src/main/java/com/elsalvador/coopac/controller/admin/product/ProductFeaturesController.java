package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductFeaturesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Tag(name = SwaggerTags.Products.TAG_NAME, description = SwaggerTags.Products.TAG_DESCRIPTION)
public class ProductFeaturesController {

    private final ManageProductFeaturesService manageProductFeaturesService;

    @PostMapping("/{productId}/features")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear caracteristica", description = "Anade una caracteristica a un producto")
    public ResponseEntity<ProductsAdminDTO.ProductFeatureDTO> addFeature(@PathVariable UUID productId, @Valid @RequestBody ProductsAdminDTO.CreateProductFeatureDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductFeaturesService.addFeature(productId, createDTO));
    }

    @PutMapping("/{productId}/features/{featureId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Actualizar caracteristica", description = "Actualiza una caracteristica existente")
    public ResponseEntity<ProductsAdminDTO.ProductFeatureDTO> updateFeature(@PathVariable UUID productId, @PathVariable UUID featureId, @Valid @RequestBody ProductsAdminDTO.UpdateProductFeatureDTO updateDTO) {
        return ResponseEntity.ok(manageProductFeaturesService.updateFeature(featureId, updateDTO));
    }

    @DeleteMapping("/{productId}/features/{featureId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Eliminar caracteristica", description = "Elimina una caracteristica de un producto")
    public ResponseEntity<Void> deleteFeature(@PathVariable UUID productId, @PathVariable UUID featureId) {
        manageProductFeaturesService.deleteFeature(featureId);
        return ResponseEntity.noContent().build();
    }
}
