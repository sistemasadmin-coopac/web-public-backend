package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductBadgesService;
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
public class ProductBadgesController {

    private final ManageProductBadgesService manageProductBadgesService;

    @PostMapping("/{productId}/badges")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear badge", description = "Anade un badge a un producto")
    public ResponseEntity<ProductsAdminDTO.ProductBadgeDTO> addBadge(@PathVariable UUID productId, @Valid @RequestBody ProductsAdminDTO.CreateProductBadgeDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductBadgesService.addBadge(productId, createDTO));
    }

    @PutMapping("/{productId}/badges/{badgeId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Actualizar badge", description = "Actualiza un badge existente")
    public ResponseEntity<ProductsAdminDTO.ProductBadgeDTO> updateBadge(@PathVariable UUID productId, @PathVariable UUID badgeId, @Valid @RequestBody ProductsAdminDTO.UpdateProductBadgeDTO updateDTO) {
        return ResponseEntity.ok(manageProductBadgesService.updateBadge(badgeId, updateDTO));
    }

    @DeleteMapping("/{productId}/badges/{badgeId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Eliminar badge", description = "Elimina un badge de un producto")
    public ResponseEntity<Void> deleteBadge(@PathVariable UUID productId, @PathVariable UUID badgeId) {
        manageProductBadgesService.deleteBadge(badgeId);
        return ResponseEntity.noContent().build();
    }
}
