package com.elsalvador.coopac.controller.admin;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.SiteSettingsAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.ManageSiteSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/site-settings")
@RequiredArgsConstructor
@Slf4j
@Tag(name = SwaggerTags.Site.TAG_NAME, description = SwaggerTags.Site.TAG_DESCRIPTION)
public class SiteSettingsController {

    private final ManageSiteSettingsService manageSiteSettingsService;

    @GetMapping
    @Operation(
        summary = SwaggerTags.Site.EMOJI_CONFIG + " Obtener configuracion del sitio",
        description = "Obtiene toda la configuracion actual del sitio incluyendo contactos, redes sociales y ubicacion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuracion obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<SiteSettingsAdminDTO.SiteSettingsResponseDTO> getSiteSettings() {
        log.debug("GET /api/admin/site-settings - Obteniendo configuracion del sitio");
        SiteSettingsAdminDTO.SiteSettingsResponseDTO settings = manageSiteSettingsService.getSiteSettings();
        return ResponseEntity.ok(settings);
    }

    @PutMapping
    @Operation(
        summary = SwaggerTags.Site.EMOJI_CONFIG + " Actualizar configuracion",
        description = "Actualiza la configuracion del sitio con contactos, redes sociales y ubicacion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuracion actualizada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<SiteSettingsAdminDTO.SiteSettingsResponseDTO> updateSiteSettings(
            @Valid @RequestBody SiteSettingsAdminDTO.UpdateSiteSettingsDTO dto) {
        log.debug("PUT /api/admin/site-settings - Actualizando configuracion del sitio");
        SiteSettingsAdminDTO.SiteSettingsResponseDTO updatedSettings = manageSiteSettingsService.updateSiteSettings(dto);
        return ResponseEntity.ok(updatedSettings);
    }
}



