package com.elsalvador.coopac.controller.admin.financial;

import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;
import com.elsalvador.coopac.service.admin.financial.ManageFinancialReportsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Controller para gestionar reportes financieros
 */
@RestController
@RequestMapping("/api/admin/financial/reports")
@RequiredArgsConstructor
@Slf4j
public class FinancialReportsController {

    private final ManageFinancialReportsService reportsService;

    /**
     * Crea un nuevo reporte financiero
     */
    @PostMapping
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> createReport(
            @Valid @RequestBody FinancialAdminDTO.FinancialReportRequest dto) {
        log.info("POST /api/admin/financial/reports - Creando nuevo reporte: {}", dto.getTitle());
        FinancialAdminDTO.FinancialReportResponse created = reportsService.createReport(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un reporte financiero existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> updateReport(
            @PathVariable UUID id,
            @Valid @RequestBody FinancialAdminDTO.FinancialReportUpdateRequest dto) {
        log.info("PUT /api/admin/financial/reports/{} - Actualizando reporte", id);
        FinancialAdminDTO.FinancialReportResponse updated = reportsService.updateReport(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un reporte financiero
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable UUID id) {
        log.info("DELETE /api/admin/financial/reports/{} - Eliminando reporte", id);
        reportsService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene un reporte por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> getReportById(@PathVariable UUID id) {
        log.debug("GET /api/admin/financial/reports/{} - Obteniendo reporte", id);
        FinancialAdminDTO.FinancialReportResponse report = reportsService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    /**
     * Obtiene todos los reportes financieros
     */
    @GetMapping
    public ResponseEntity<List<FinancialAdminDTO.FinancialReportResponse>> getAllReports(
            @RequestParam(required = false) UUID categoryId) {
        log.debug("GET /api/admin/financial/reports - Obteniendo reportes");

        List<FinancialAdminDTO.FinancialReportResponse> reports;
        if (categoryId != null) {
            reports = reportsService.getReportsByCategory(categoryId);
        } else {
            reports = reportsService.getAllReports();
        }

        return ResponseEntity.ok(reports);
    }

    /**
     * Sube un archivo de reporte
     */
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FinancialAdminDTO.FileUploadResponse> uploadReportFile(
            @RequestParam("file") MultipartFile file) {
        log.info("POST /api/admin/financial/reports/upload-file - Subiendo archivo: {}", file.getOriginalFilename());
        FinancialAdminDTO.FileUploadResponse response = reportsService.uploadReportFile(file);
        return ResponseEntity.ok(response);
    }

    /**
     * Sube una miniatura para un reporte
     */
    @PostMapping(value = "/upload-thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FinancialAdminDTO.FileUploadResponse> uploadThumbnail(
            @RequestParam("file") MultipartFile file) {
        log.info("POST /api/admin/financial/reports/upload-thumbnail - Subiendo miniatura: {}", file.getOriginalFilename());
        FinancialAdminDTO.FileUploadResponse response = reportsService.uploadThumbnail(file);
        return ResponseEntity.ok(response);
    }
}
