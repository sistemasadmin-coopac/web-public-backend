package com.elsalvador.coopac.service.admin.financial;

import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar reportes financieros
 */
public interface ManageFinancialReportsService {

    /**
     * Crea un nuevo reporte financiero con archivos integrados
     */
    FinancialAdminDTO.FinancialReportResponse createReport(
            FinancialAdminDTO.FinancialReportRequest dto,
            MultipartFile file,
            MultipartFile thumbnail);

    /**
     * Actualiza un reporte financiero existente con archivos opcionales
     */
    FinancialAdminDTO.FinancialReportResponse updateReport(
            UUID id,
            FinancialAdminDTO.FinancialReportUpdateRequest dto,
            MultipartFile file,
            MultipartFile thumbnail);

    /**
     * Elimina un reporte financiero y sus archivos asociados
     */
    void deleteReport(UUID id);

    /**
     * Obtiene un reporte por ID
     */
    FinancialAdminDTO.FinancialReportResponse getReportById(UUID id);

    /**
     * Obtiene todos los reportes
     */
    List<FinancialAdminDTO.FinancialReportResponse> getAllReports();

    /**
     * Obtiene reportes por categoría
     */
    List<FinancialAdminDTO.FinancialReportResponse> getReportsByCategory(UUID categoryId);
}
