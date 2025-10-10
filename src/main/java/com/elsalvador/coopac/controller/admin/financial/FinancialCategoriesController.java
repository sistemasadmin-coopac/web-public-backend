package com.elsalvador.coopac.controller.admin.financial;

import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;
import com.elsalvador.coopac.service.admin.financial.ManageFinancialCategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para gestionar categorías de reportes financieros
 */
@RestController
@RequestMapping("/api/admin/financial/categories")
@RequiredArgsConstructor
@Slf4j
public class FinancialCategoriesController {

    private final ManageFinancialCategoriesService categoriesService;

    /**
     * Crea una nueva categoría de reportes
     */
    @PostMapping
    public ResponseEntity<FinancialAdminDTO.FinancialReportCategoryResponse> createCategory(
            @Valid @RequestBody FinancialAdminDTO.FinancialReportCategoryRequest dto) {
        log.info("POST /api/admin/financial/categories - Creando nueva categoría: {}", dto.getName());
        FinancialAdminDTO.FinancialReportCategoryResponse created = categoriesService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza una categoría existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<FinancialAdminDTO.FinancialReportCategoryResponse> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody FinancialAdminDTO.FinancialReportCategoryRequest dto) {
        log.info("PUT /api/admin/financial/categories/{} - Actualizando categoría", id);
        FinancialAdminDTO.FinancialReportCategoryResponse updated = categoriesService.updateCategory(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina una categoría
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        log.info("DELETE /api/admin/financial/categories/{} - Eliminando categoría", id);
        categoriesService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una categoría por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<FinancialAdminDTO.FinancialReportCategoryResponse> getCategoryById(@PathVariable UUID id) {
        log.debug("GET /api/admin/financial/categories/{} - Obteniendo categoría", id);
        FinancialAdminDTO.FinancialReportCategoryResponse category = categoriesService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /**
     * Obtiene todas las categorías
     */
    @GetMapping
    public ResponseEntity<List<FinancialAdminDTO.FinancialReportCategoryResponse>> getAllCategories() {
        log.debug("GET /api/admin/financial/categories - Obteniendo todas las categorías");
        List<FinancialAdminDTO.FinancialReportCategoryResponse> categories = categoriesService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
