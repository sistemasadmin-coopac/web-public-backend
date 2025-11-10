package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductFinancialInfoService;
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
public class ProductFinancialInfoController {

    private final ManageProductFinancialInfoService manageProductFinancialInfoService;

    @PostMapping("/{productId}/financial-info")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Crear informacion financiera", description = "Crea informacion financiera para un producto")
    public ResponseEntity<ProductsAdminDTO.ProductFinancialInfoDTO> createFinancialInfo(@PathVariable UUID productId, @Valid @RequestBody ProductsAdminDTO.CreateProductFinancialInfoDTO createDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manageProductFinancialInfoService.createFinancialInfo(productId, createDTO));
    }

    @PutMapping("/{productId}/financial-info")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Actualizar informacion financiera", description = "Actualiza la informacion financiera de un producto")
    public ResponseEntity<ProductsAdminDTO.ProductFinancialInfoDTO> updateFinancialInfo(@PathVariable UUID productId, @Valid @RequestBody ProductsAdminDTO.UpdateProductFinancialInfoDTO updateDTO) {
        return ResponseEntity.ok(manageProductFinancialInfoService.updateFinancialInfo(productId, updateDTO));
    }

    @DeleteMapping("/{productId}/financial-info")
    @Operation(summary = SwaggerTags.Products.EMOJI_GENERAL + " Eliminar informacion financiera", description = "Elimina la informacion financiera de un producto")
    public ResponseEntity<Void> deleteFinancialInfo(@PathVariable UUID productId) {
        manageProductFinancialInfoService.deleteFinancialInfo(productId);
        return ResponseEntity.noContent().build();
    }
}
