package com.elsalvador.coopac.controller.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.service.admin.product.ManageProductFinancialInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para administración de información financiera de productos
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductFinancialInfoController {

    private final ManageProductFinancialInfoService manageProductFinancialInfoService;

    /**
     * Crea información financiera para un producto
     */
    @PostMapping("/{productId}/financial-info")
    public ResponseEntity<ProductsAdminDTO.ProductFinancialInfoDTO> createFinancialInfo(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductsAdminDTO.CreateProductFinancialInfoDTO createDTO) {
        ProductsAdminDTO.ProductFinancialInfoDTO financialInfo =
                manageProductFinancialInfoService.createFinancialInfo(productId, createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(financialInfo);
    }

    /**
     * Actualiza la información financiera de un producto
     */
    @PutMapping("/{productId}/financial-info")
    public ResponseEntity<ProductsAdminDTO.ProductFinancialInfoDTO> updateFinancialInfo(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductsAdminDTO.UpdateProductFinancialInfoDTO updateDTO) {
        ProductsAdminDTO.ProductFinancialInfoDTO financialInfo =
                manageProductFinancialInfoService.updateFinancialInfo(productId, updateDTO);
        return ResponseEntity.ok(financialInfo);
    }

    /**
     * Elimina la información financiera de un producto
     */
    @DeleteMapping("/{productId}/financial-info")
    public ResponseEntity<Void> deleteFinancialInfo(@PathVariable UUID productId) {
        manageProductFinancialInfoService.deleteFinancialInfo(productId);
        return ResponseEntity.noContent().build();
    }
}
