package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.HomePromotionFeaturesAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionFeaturesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-promotion-features")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Home.TAG_NAME,
    description = SwaggerTags.Home.TAG_DESCRIPTION
)
public class HomePromotionFeaturesController {

    private final ManageHomePromotionFeaturesService manageService;

    @GetMapping("/promotion/{promotionId}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Obtener features de una promoción",
        description = "Obtiene todas las características/features asociadas a una promoción"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Features obtenidas exitosamente",
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
    public ResponseEntity<List<HomePromotionFeaturesAdminDTO>> getFeaturesByPromotion(
            @PathVariable
            @Parameter(description = "ID de la promoción")
            UUID promotionId) {
        List<HomePromotionFeaturesAdminDTO> features = manageService.getFeaturesByPromotion(promotionId);
        return ResponseEntity.ok(features);
    }

    @PostMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Crear nueva feature",
        description = "Crea una nueva característica/feature para una promoción"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Feature creada exitosamente",
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
    public ResponseEntity<HomePromotionFeaturesAdminDTO> createFeature(
            @Valid @RequestBody HomePromotionFeaturesAdminDTO featureDTO) {
        HomePromotionFeaturesAdminDTO createdFeature = manageService.createFeature(featureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeature);
    }

    @PutMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Actualizar feature",
        description = "Actualiza una característica/feature existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Feature actualizada exitosamente"),
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
    public ResponseEntity<HomePromotionFeaturesAdminDTO> updateFeature(
            @Valid @RequestBody HomePromotionFeaturesAdminDTO featureDTO) {
        HomePromotionFeaturesAdminDTO updatedFeature = manageService.updateFeature(featureDTO);
        return ResponseEntity.ok(updatedFeature);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Eliminar feature",
        description = "Elimina una característica/feature de una promoción"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Feature eliminada exitosamente"),
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
            description = "Feature no encontrada",
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
    public ResponseEntity<Void> deleteFeature(
            @PathVariable
            @Parameter(description = "ID de la feature a eliminar")
            UUID id) {
        manageService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/promotion/{promotionId}/all")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Actualizar todas las features",
        description = "Actualiza todas las características/features de una promoción"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Features actualizadas exitosamente"),
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
    public ResponseEntity<List<HomePromotionFeaturesAdminDTO>> updatePromotionFeatures(
            @PathVariable
            @Parameter(description = "ID de la promoción")
            UUID promotionId,
            @Valid @RequestBody List<HomePromotionFeaturesAdminDTO> features) {
        List<HomePromotionFeaturesAdminDTO> updatedFeatures =
            manageService.updatePromotionFeatures(promotionId, features);
        return ResponseEntity.ok(updatedFeatures);
    }
}

