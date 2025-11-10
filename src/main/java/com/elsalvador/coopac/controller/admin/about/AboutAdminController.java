package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.about.GetAboutAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para obtener datos completos de la página About
 */
@RestController
@RequestMapping("/api/admin/about")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.About.TAG_NAME,
    description = SwaggerTags.About.TAG_DESCRIPTION
)
public class AboutAdminController {

    private final GetAboutAdminService getAboutAdminService;

    /**
     * Obtiene todos los datos completos de la página About para administración
     */
    @GetMapping("/complete")
    @Operation(
        summary = SwaggerTags.About.EMOJI_GENERAL + " Obtener todos los datos de About",
        description = "Retorna todos los datos de la página About incluyendo misión, visión, valores, historia, timeline, impacto, métricas y miembros de junta directiva"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Datos obtenidos exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutPageResponseDTO.class)
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
    public ResponseEntity<AboutAdminDTO.AboutPageResponseDTO> getAboutCompleteData() {
        AboutAdminDTO.AboutPageResponseDTO aboutData = getAboutAdminService.getAboutCompleteData();
        return ResponseEntity.ok(aboutData);
    }
}
