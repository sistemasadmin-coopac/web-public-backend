package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductStepsService;
import io.swagger.v3.oas.annotations.Operation;
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
public class ProductStepsController {

    private final ManageProductStepsService manageProductStepsService;

    @PostMapping("/{productId}/steps")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear paso", description = "Anade un paso a un producto")
    public ResponseEntity<ProductsAdminDTO.ProductStepDTO> addStep(@PathVariable UUID productId, @Valid @RequestBody ProductsAdminDTO.CreateProductStepDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductStepsService.addStep(productId, createDTO));
    }

    @PostMapping("/{productId}/steps/batch")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear multiples pasos", description = "Anade multiples pasos a un producto en una sola operacion")
    public ResponseEntity<List<ProductsAdminDTO.ProductStepDTO>> addMultipleSteps(@PathVariable UUID productId, @Valid @RequestBody List<ProductsAdminDTO.CreateProductStepDTO> stepsDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductStepsService.addMultipleSteps(productId, stepsDTO));
    }

    @PutMapping("/{productId}/steps/{stepId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Actualizar paso", description = "Actualiza un paso existente")
    public ResponseEntity<ProductsAdminDTO.ProductStepDTO> updateStep(@PathVariable UUID productId, @PathVariable UUID stepId, @Valid @RequestBody ProductsAdminDTO.UpdateProductStepDTO updateDTO) {
        return ResponseEntity.ok(manageProductStepsService.updateStep(stepId, updateDTO));
    }

    @PatchMapping("/{productId}/steps/{stepId}/status")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Cambiar estado del paso", description = "Activa o desactiva un paso")
    public ResponseEntity<ProductsAdminDTO.ProductStepDTO> toggleStepStatus(@PathVariable UUID productId, @PathVariable UUID stepId, @RequestParam Boolean isActive) {
        return ResponseEntity.ok(manageProductStepsService.toggleStepStatus(stepId, isActive));
    }

    @DeleteMapping("/{productId}/steps/{stepId}")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Eliminar paso", description = "Elimina un paso de un producto")
    public ResponseEntity<Void> deleteStep(@PathVariable UUID productId, @PathVariable UUID stepId) {
        manageProductStepsService.deleteStep(stepId);
        return ResponseEntity.noContent().build();
    }
}
