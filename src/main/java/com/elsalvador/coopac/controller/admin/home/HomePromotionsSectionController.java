package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsSectionService;
import com.elsalvador.coopac.service.admin.home.UpdateHomePromotionsSectionService;
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
@RequestMapping("/api/admin/home-promotions-section")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Home.TAG_NAME,
    description = SwaggerTags.Home.TAG_DESCRIPTION
)
public class HomePromotionsSectionController {

    private final GetHomePromotionsSectionService getService;
    private final UpdateHomePromotionsSectionService updateService;

    @GetMapping("/active")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Obtener sección activa de promociones",
        description = "Obtiene la configuración de la sección de promociones que está activa"
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
    public ResponseEntity<HomePromotionsSectionAdminDTO> getActivePromotionsSection() {
        HomePromotionsSectionAdminDTO section = getService.getActivePromotionsSection();
        return ResponseEntity.ok(section);
    }

    @PutMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Actualizar sección de promociones",
        description = "Actualiza la configuración de la sección de promociones"
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
    public ResponseEntity<HomePromotionsSectionAdminDTO> updatePromotionsSection(
            @Valid @RequestBody HomePromotionsSectionAdminDTO sectionDTO) {
        HomePromotionsSectionAdminDTO updatedSection = updateService.updatePromotionsSection(sectionDTO);
        return ResponseEntity.ok(updatedSection);
    }
}

