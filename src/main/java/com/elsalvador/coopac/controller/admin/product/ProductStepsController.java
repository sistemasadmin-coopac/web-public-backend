package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductStepsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para administración de pasos de productos
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductStepsController {

    private final ManageProductStepsService manageProductStepsService;

    /**
     * Añade un paso a un producto
     */
    @PostMapping("/{productId}/steps")
    public ResponseEntity<ProductsAdminDTO.ProductStepDTO> addStep(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductsAdminDTO.CreateProductStepDTO createDTO) {
        ProductsAdminDTO.ProductStepDTO step =
                manageProductStepsService.addStep(productId, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(step);
    }

    /**
     * Actualiza un paso
     */
    @PutMapping("/{productId}/steps/{stepId}")
    public ResponseEntity<ProductsAdminDTO.ProductStepDTO> updateStep(
            @PathVariable UUID productId,
            @PathVariable UUID stepId,
            @Valid @RequestBody ProductsAdminDTO.UpdateProductStepDTO updateDTO) {
        ProductsAdminDTO.ProductStepDTO step =
                manageProductStepsService.updateStep(stepId, updateDTO);
        return ResponseEntity.ok(step);
    }

    /**
     * Activa o desactiva un paso
     */
    @PatchMapping("/{productId}/steps/{stepId}/status")
    public ResponseEntity<ProductsAdminDTO.ProductStepDTO> toggleStepStatus(
            @PathVariable UUID productId,
            @PathVariable UUID stepId,
            @RequestParam Boolean isActive) {
        ProductsAdminDTO.ProductStepDTO step =
                manageProductStepsService.toggleStepStatus(stepId, isActive);
        return ResponseEntity.ok(step);
    }

    /**
     * Elimina un paso
     */
    @DeleteMapping("/{productId}/steps/{stepId}")
    public ResponseEntity<Void> deleteStep(
            @PathVariable UUID productId,
            @PathVariable UUID stepId) {
        manageProductStepsService.deleteStep(stepId);
        return ResponseEntity.noContent().build();
    }
}
