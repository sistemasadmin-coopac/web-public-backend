package com.elsalvador.coopac.service.admin.financial;

import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar categorías de reportes financieros
 */
public interface ManageFinancialCategoriesService {

    /**
     * Crea una nueva categoría
     */
    FinancialAdminDTO.FinancialReportCategoryResponse createCategory(FinancialAdminDTO.FinancialReportCategoryRequest dto);

    /**
     * Actualiza una categoría existente
     */
    FinancialAdminDTO.FinancialReportCategoryResponse updateCategory(UUID id, FinancialAdminDTO.FinancialReportCategoryRequest dto);

    /**
     * Elimina una categoría
     */
    void deleteCategory(UUID id);

    /**
     * Obtiene una categoría por ID
     */
    FinancialAdminDTO.FinancialReportCategoryResponse getCategoryById(UUID id);

    /**
     * Obtiene todas las categorías
     */
    List<FinancialAdminDTO.FinancialReportCategoryResponse> getAllCategories();
}
