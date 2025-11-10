package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.home.GetHomeStatsSectionService;
import com.elsalvador.coopac.service.admin.home.UpdateHomeStatsSectionService;
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

@RestController
@RequestMapping("/api/admin/home-stats-section")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Home.TAG_NAME,
    description = SwaggerTags.Home.TAG_DESCRIPTION
)
public class HomeStatsSectionController {

    private final GetHomeStatsSectionService getService;
    private final UpdateHomeStatsSectionService updateService;

    @GetMapping("/active")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Obtener sección activa de estadísticas",
        description = "Obtiene la configuración de la sección de estadísticas que está activa"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sección obtenida exitosamente"),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<HomeStatsSectionDTO> getActiveStatsSection() {
        HomeStatsSectionDTO section = getService.getActiveStatsSection();
        return ResponseEntity.ok(section);
    }

    @PutMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Actualizar sección de estadísticas",
        description = "Actualiza la configuración de la sección de estadísticas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sección actualizada exitosamente"),
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
            description = "No autorizado (requiere rol ADMIN)",
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
    public ResponseEntity<HomeStatsSectionDTO> updateStatsSection(
            @Valid @RequestBody HomeStatsSectionDTO sectionDTO) {
        HomeStatsSectionDTO updatedSection = updateService.updateStatsSection(sectionDTO);
        return ResponseEntity.ok(updatedSection);
    }
}
