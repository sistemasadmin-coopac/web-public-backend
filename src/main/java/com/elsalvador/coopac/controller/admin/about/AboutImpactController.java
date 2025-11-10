package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutImpactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/about/impact")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.About.TAG_NAME,
    description = SwaggerTags.About.TAG_DESCRIPTION
)
public class AboutImpactController {

    private final ManageAboutImpactService manageAboutImpactService;

    @PutMapping("/metrics/{id}")
    @Operation(
        summary = SwaggerTags.About.EMOJI_IMPACT + " Actualizar métrica de impacto",
        description = "Actualiza los datos de una métrica de impacto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Métrica actualizada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutImpactMetricDTO.class)
            )
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
            description = "Métrica no encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutImpactMetricDTO> updateImpactMetric(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutImpactMetricDTO dto) {
        AboutAdminDTO.AboutImpactMetricDTO updated = manageAboutImpactService.updateImpactMetric(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/section")
    @Operation(
        summary = SwaggerTags.About.EMOJI_IMPACT + " Actualizar sección de impacto",
        description = "Actualiza la configuración de la sección de impacto"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuración actualizada"),
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
            description = "Error interno",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutImpactSectionDTO> updateImpactSection(
            @Valid @RequestBody AboutAdminDTO.AboutImpactSectionDTO dto) {
        AboutAdminDTO.AboutImpactSectionDTO updated = manageAboutImpactService.updateImpactSection(dto);
        return ResponseEntity.ok(updated);
    }
}

