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
     * Crea un nuevo reporte financiero
     */
    FinancialAdminDTO.FinancialReportResponse createReport(FinancialAdminDTO.FinancialReportRequest dto);

    /**
     * Actualiza un reporte financiero existente
     */
    FinancialAdminDTO.FinancialReportResponse updateReport(UUID id, FinancialAdminDTO.FinancialReportUpdateRequest dto);

    /**
     * Elimina un reporte financiero
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

    /**
     * Sube un archivo de reporte
     */
    FinancialAdminDTO.FileUploadResponse uploadReportFile(MultipartFile file);

    /**
     * Sube una miniatura para un reporte
     */
    FinancialAdminDTO.FileUploadResponse uploadThumbnail(MultipartFile file);
}
