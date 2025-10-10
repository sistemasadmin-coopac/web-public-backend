package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductActionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para administración de acciones de productos
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductActionsController {

    private final ManageProductActionsService manageProductActionsService;

    /**
     * Añade una acción a un producto
     */
    @PostMapping("/{productId}/actions")
    public ResponseEntity<ProductsAdminDTO.ProductActionDTO> addAction(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductsAdminDTO.CreateProductActionDTO createDTO) {
        ProductsAdminDTO.ProductActionDTO action =
                manageProductActionsService.addAction(productId, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(action);
    }

    /**
     * Actualiza una acción
     */
    @PutMapping("/{productId}/actions/{actionId}")
    public ResponseEntity<ProductsAdminDTO.ProductActionDTO> updateAction(
            @PathVariable UUID productId,
            @PathVariable UUID actionId,
            @Valid @RequestBody ProductsAdminDTO.UpdateProductActionDTO updateDTO) {
        ProductsAdminDTO.ProductActionDTO action =
                manageProductActionsService.updateAction(actionId, updateDTO);
        return ResponseEntity.ok(action);
    }

    /**
     * Elimina una acción
     */
    @DeleteMapping("/{productId}/actions/{actionId}")
    public ResponseEntity<Void> deleteAction(
            @PathVariable UUID productId,
            @PathVariable UUID actionId) {
        manageProductActionsService.deleteAction(actionId);
        return ResponseEntity.noContent().build();
    }
}
