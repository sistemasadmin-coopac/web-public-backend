package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/about/history")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.About.TAG_NAME,
    description = SwaggerTags.About.TAG_DESCRIPTION
)
public class AboutHistoryController {

    private final ManageAboutHistoryService manageAboutHistoryService;

    @PostMapping("/timeline")
    @Operation(
        summary = SwaggerTags.About.EMOJI_HISTORY + " Crear evento del timeline",
        description = "Crea un nuevo evento para el timeline de historia"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Evento creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutTimelineEventDTO.class)
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
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutTimelineEventDTO> createTimelineEvent(
            @Valid @RequestBody AboutAdminDTO.AboutTimelineEventDTO dto) {
        AboutAdminDTO.AboutTimelineEventDTO created = manageAboutHistoryService.createTimelineEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/timeline/{id}")
    @Operation(
        summary = SwaggerTags.About.EMOJI_HISTORY + " Actualizar evento del timeline",
        description = "Actualiza un evento del timeline existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Evento actualizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutTimelineEventDTO.class)
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
            description = "Evento no encontrado",
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
    public ResponseEntity<AboutAdminDTO.AboutTimelineEventDTO> updateTimelineEvent(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutTimelineEventDTO dto) {
        AboutAdminDTO.AboutTimelineEventDTO updated = manageAboutHistoryService.updateTimelineEvent(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/timeline/{id}")
    @Operation(
        summary = SwaggerTags.About.EMOJI_HISTORY + " Eliminar evento del timeline",
        description = "Elimina un evento del timeline"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evento eliminado"),
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
            description = "Evento no encontrado",
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
    public ResponseEntity<Void> deleteTimelineEvent(@PathVariable UUID id) {
        manageAboutHistoryService.deleteTimelineEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/section")
    @Operation(
        summary = SwaggerTags.About.EMOJI_HISTORY + " Actualizar sección de historia",
        description = "Actualiza la configuración de la sección de historia"
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
    public ResponseEntity<AboutAdminDTO.AboutHistorySectionDTO> updateHistorySection(
            @Valid @RequestBody AboutAdminDTO.AboutHistorySectionDTO dto) {
        AboutAdminDTO.AboutHistorySectionDTO updated = manageAboutHistoryService.updateHistorySection(dto);
        return ResponseEntity.ok(updated);
    }
}

