package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutMissionVisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/about/mission-vision")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.About.TAG_NAME,
    description = SwaggerTags.About.TAG_DESCRIPTION
)
public class AboutMissionVisionController {

    private final ManageAboutMissionVisionService manageAboutMissionVisionService;

    @PutMapping
    @Operation(
        summary = SwaggerTags.About.EMOJI_MISSION + " Actualizar misión y visión",
        description = "Actualiza los títulos y textos de la misión y visión"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutMissionVisionDTO.class)
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
            description = "Error interno",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutMissionVisionDTO> updateMissionVision(
            @Valid @RequestBody AboutAdminDTO.AboutMissionVisionDTO dto) {
        AboutAdminDTO.AboutMissionVisionDTO updated = manageAboutMissionVisionService.saveMissionVision(dto);
        return ResponseEntity.ok(updated);
    }
}

