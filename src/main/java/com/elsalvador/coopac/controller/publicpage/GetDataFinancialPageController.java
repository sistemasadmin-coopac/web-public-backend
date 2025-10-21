package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.financial.FinancialPageDTO;
import com.elsalvador.coopac.service.publicpages.DownloadFinancialReportService;
import com.elsalvador.coopac.service.publicpages.GetDataFinancialPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/financials/page")
@RequiredArgsConstructor
@Slf4j
public class GetDataFinancialPageController {

    private final GetDataFinancialPageService getDataFinancialPageService;
    private final DownloadFinancialReportService downloadFinancialReportService;

    @GetMapping
    public FinancialPageDTO getFinancial() {
        log.info("Solicitada página Financial");
        return getDataFinancialPageService.getFinancialPageData();
    }

    @GetMapping("/download/{reportId}")
    public ResponseEntity<Resource> downloadReport(@PathVariable UUID reportId) {
        log.info("Solicitada descarga pública del reporte con ID: {}", reportId);

        Resource resource = downloadFinancialReportService.downloadReport(reportId);
        String fileName = downloadFinancialReportService.getFileName(reportId);

        // Determinar el Content-Type basándose en la extensión del archivo
        String contentType = determineContentType(fileName);

        log.info("Descargando archivo: {} con Content-Type: {}", fileName, contentType);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    private String determineContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default -> "application/octet-stream";
        };
    }
}
