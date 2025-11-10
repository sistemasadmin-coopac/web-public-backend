package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.HomeCtaBlocksAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.home.GetHomeCtaBlocksService;
import com.elsalvador.coopac.service.admin.home.UpdateHomeCtaBlocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-cta-blocks")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Home.TAG_NAME,
    description = SwaggerTags.Home.TAG_DESCRIPTION
)
public class HomeCtaBlocksController {

    private final GetHomeCtaBlocksService getHomeCtaBlocksService;
    private final UpdateHomeCtaBlocksService updateHomeCtaBlocksService;

    @GetMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_CTA_BLOCKS + " Obtener todos los bloques CTA activos",
        description = "Obtiene la lista de bloques Call To Action (CTA) activos del home"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bloques CTA obtenidos exitosamente",
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
    public ResponseEntity<List<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO>> getAllActiveCtaBlocks() {
        List<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO> ctaBlocks =
                getHomeCtaBlocksService.getAllActiveCtaBlocks();
        return ResponseEntity.ok(ctaBlocks);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_CTA_BLOCKS + " Actualizar bloque CTA",
        description = "Actualiza un bloque Call To Action específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Bloque CTA actualizado exitosamente",
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
            description = "No autorizado (requiere rol ADMIN)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Bloque CTA no encontrado",
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
    public ResponseEntity<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO> updateCtaBlock(
            @PathVariable
            @Parameter(description = "ID del bloque CTA a actualizar")
            UUID id,
            @Valid @RequestBody HomeCtaBlocksAdminDTO.UpdateHomeCtaBlockDTO updateDTO) {
        HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO updatedCtaBlock =
                updateHomeCtaBlocksService.updateCtaBlock(id, updateDTO);
        return ResponseEntity.ok(updatedCtaBlock);
    }
}

