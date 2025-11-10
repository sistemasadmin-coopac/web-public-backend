package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.financial.FinancialPageDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.DownloadFinancialReportService;
import com.elsalvador.coopac.service.publicpages.GetDataFinancialPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataFinancialPageController {

    private final GetDataFinancialPageService getDataFinancialPageService;
    private final DownloadFinancialReportService downloadFinancialReportService;

    @GetMapping
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_GENERAL + " Obtener datos de pagina Financial",
        description = "Obtiene la informacion completa de la pagina de reportes financieros incluyendo categorias y reportes disponibles"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la pagina Financial obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public FinancialPageDTO getFinancial() {
        log.info("Solicitada pagina Financial");
        return getDataFinancialPageService.getFinancialPageData();
    }

    @GetMapping("/download/{reportId}")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_GENERAL + " Descargar reporte financiero",
        description = "Descarga un reporte financiero en el formato especificado (PDF, Excel, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte descargado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Resource> downloadReport(
            @PathVariable
            @Parameter(description = "ID unico del reporte a descargar")
            UUID reportId) {
        log.info("Solicitada descarga publica del reporte con ID: {}", reportId);

        Resource resource = downloadFinancialReportService.downloadReport(reportId);
        String fileName = downloadFinancialReportService.getFileName(reportId);

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


