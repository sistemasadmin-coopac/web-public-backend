package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductActionsService;
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
public class ProductActionsController {

    private final ManageProductActionsService manageProductActionsService;

    @PostMapping("/{productId}/actions")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear accion", description = "Anade una accion a un producto")
    public ResponseEntity<ProductsAdminDTO.ProductActionDTO> addAction(@PathVariable UUID productId, @Valid @RequestBody ProductsAdminDTO.CreateProductActionDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductActionsService.addAction(productId, createDTO));
    }

    @PutMapping("/{productId}/actions/{actionId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Actualizar accion", description = "Actualiza una accion existente")
    public ResponseEntity<ProductsAdminDTO.ProductActionDTO> updateAction(@PathVariable UUID productId, @PathVariable UUID actionId, @Valid @RequestBody ProductsAdminDTO.UpdateProductActionDTO updateDTO) {
        return ResponseEntity.ok(manageProductActionsService.updateAction(actionId, updateDTO));
    }

    @DeleteMapping("/{productId}/actions/{actionId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Eliminar accion", description = "Elimina una accion de un producto")
    public ResponseEntity<Void> deleteAction(@PathVariable UUID productId, @PathVariable UUID actionId) {
        manageProductActionsService.deleteAction(actionId);
        return ResponseEntity.noContent().build();
    }
}
