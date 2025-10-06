package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductBadgesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para administración de badges de productos
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductBadgesController {

    private final ManageProductBadgesService manageProductBadgesService;

    /**
     * Añade un badge a un producto
     */
    @PostMapping("/{productId}/badges")
    public ResponseEntity<ProductsAdminDTO.ProductBadgeDTO> addBadge(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductsAdminDTO.CreateProductBadgeDTO createDTO) {
        ProductsAdminDTO.ProductBadgeDTO badge =
                manageProductBadgesService.addBadge(productId, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(badge);
    }

    /**
     * Actualiza un badge
     */
    @PutMapping("/{productId}/badges/{badgeId}")
    public ResponseEntity<ProductsAdminDTO.ProductBadgeDTO> updateBadge(
            @PathVariable UUID productId,
            @PathVariable UUID badgeId,
            @Valid @RequestBody ProductsAdminDTO.UpdateProductBadgeDTO updateDTO) {
        ProductsAdminDTO.ProductBadgeDTO badge =
                manageProductBadgesService.updateBadge(badgeId, updateDTO);
        return ResponseEntity.ok(badge);
    }

    /**
     * Elimina un badge
     */
    @DeleteMapping("/{productId}/badges/{badgeId}")
    public ResponseEntity<Void> deleteBadge(
            @PathVariable UUID productId,
            @PathVariable UUID badgeId) {
        manageProductBadgesService.deleteBadge(badgeId);
        return ResponseEntity.noContent().build();
    }
}
