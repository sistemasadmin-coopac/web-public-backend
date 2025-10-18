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
     * Crea un nuevo reporte financiero con archivos
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> createReport(
            @Valid @ModelAttribute FinancialAdminDTO.FinancialReportRequest dto,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail) {

        log.info("POST /api/admin/financial/reports - Creando nuevo reporte: {}", dto.getTitle());

        FinancialAdminDTO.FinancialReportResponse created = reportsService.createReport(dto, file, thumbnail);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un reporte financiero existente
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> updateReport(
            @PathVariable UUID id,
            @Valid @ModelAttribute FinancialAdminDTO.FinancialReportUpdateRequest dto,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail) {

        log.info("PUT /api/admin/financial/reports/{} - Actualizando reporte", id);

        FinancialAdminDTO.FinancialReportResponse updated = reportsService.updateReport(id, dto, file, thumbnail);

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
}
