package com.elsalvador.coopac.controller.admin.financial;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.FinancialAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.financial.ManageFinancialReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/admin/financial/reports")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = SwaggerTags.Financials.TAG_NAME,
    description = SwaggerTags.Financials.TAG_DESCRIPTION
)
public class FinancialReportsController {

    private final ManageFinancialReportsService reportsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = SwaggerTags.Financials.EMOJI_REPORTS + " Crear nuevo reporte",
        description = "Crea un nuevo reporte financiero con archivos adjuntos"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Reporte creado exitosamente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> createReport(
            @Valid @ModelAttribute FinancialAdminDTO.FinancialReportRequest dto,
            @RequestParam(value = "file", required = false)
            @Parameter(description = "Archivo PDF del reporte (opcional)")
            MultipartFile file,
            @RequestParam(value = "thumbnail", required = false)
            @Parameter(description = "Imagen miniatura del reporte (opcional)")
            MultipartFile thumbnail) {

        log.info("POST /api/admin/financial/reports - Creando nuevo reporte: {}", dto.getTitle());

        FinancialAdminDTO.FinancialReportResponse created = reportsService.createReport(dto, file, thumbnail);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = SwaggerTags.Financials.EMOJI_REPORTS + " Actualizar reporte",
        description = "Actualiza un reporte financiero existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte actualizado exitosamente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Reporte no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> updateReport(
            @PathVariable
            @Parameter(description = "ID del reporte a actualizar")
            UUID id,
            @Valid @ModelAttribute FinancialAdminDTO.FinancialReportUpdateRequest dto,
            @RequestParam(value = "file", required = false)
            @Parameter(description = "Nuevo archivo PDF (opcional)")
            MultipartFile file,
            @RequestParam(value = "thumbnail", required = false)
            @Parameter(description = "Nueva imagen miniatura (opcional)")
            MultipartFile thumbnail) {

        log.info("PUT /api/admin/financial/reports/{} - Actualizando reporte", id);

        FinancialAdminDTO.FinancialReportResponse updated = reportsService.updateReport(id, dto, file, thumbnail);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Financials.EMOJI_REPORTS + " Eliminar reporte",
        description = "Elimina un reporte financiero"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reporte eliminado exitosamente"),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Reporte no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<Void> deleteReport(@PathVariable UUID id) {
        log.info("DELETE /api/admin/financial/reports/{} - Eliminando reporte", id);
        reportsService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Financials.EMOJI_REPORTS + " Obtener reporte por ID",
        description = "Obtiene un reporte financiero específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte obtenido exitosamente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Reporte no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<FinancialAdminDTO.FinancialReportResponse> getReportById(@PathVariable UUID id) {
        log.debug("GET /api/admin/financial/reports/{} - Obteniendo reporte", id);
        FinancialAdminDTO.FinancialReportResponse report = reportsService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    @GetMapping
    @Operation(
        summary = SwaggerTags.Financials.EMOJI_REPORTS + " Obtener todos los reportes",
        description = "Obtiene la lista de reportes financieros, opcionalmente filtrados por categoría"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reportes obtenidos exitosamente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<FinancialAdminDTO.FinancialReportResponse>> getAllReports(
            @RequestParam(required = false)
            @Parameter(description = "ID de categoría para filtrar (opcional)")
            UUID categoryId) {
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

