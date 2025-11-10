package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-promotions")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Home.TAG_NAME,
    description = SwaggerTags.Home.TAG_DESCRIPTION
)
public class HomePromotionsController {

    private final GetHomePromotionsService getService;
    private final ManageHomePromotionsService manageService;
    private final ObjectMapper objectMapper;

    private static final long MAX_IMAGE_SIZE = 2 * 1024 * 1024;

    @GetMapping("/section/{sectionId}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Obtener promociones activas de sección",
        description = "Obtiene las promociones activas de una sección específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promociones obtenidas exitosamente"),
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
    public ResponseEntity<List<HomePromotionsAdminDTO>> getPromotionsBySection(
            @PathVariable
            @Parameter(description = "ID de la sección")
            UUID sectionId) {
        List<HomePromotionsAdminDTO> promotions = getService.getPromotionsBySection(sectionId);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/section/{sectionId}/all")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Obtener todas las promociones de sección",
        description = "Obtiene todas las promociones (activas e inactivas) de una sección"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promociones obtenidas exitosamente"),
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
    public ResponseEntity<List<HomePromotionsAdminDTO>> getAllPromotionsBySection(
            @PathVariable
            @Parameter(description = "ID de la sección")
            UUID sectionId) {
        List<HomePromotionsAdminDTO> promotions = getService.getAllPromotionsBySection(sectionId);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Obtener promoción por ID",
        description = "Obtiene los detalles de una promoción específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promoción obtenida exitosamente"),
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
            description = "Promoción no encontrada",
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
    public ResponseEntity<HomePromotionsAdminDTO> getPromotionById(
            @PathVariable
            @Parameter(description = "ID de la promoción")
            UUID id) {
        HomePromotionsAdminDTO promotion = getService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Crear nueva promoción",
        description = "Crea una nueva promoción con imagen opcional (máx 2MB)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Promoción creada exitosamente",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos o imagen demasiado grande",
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
    public ResponseEntity<HomePromotionsAdminDTO> createPromotion(
            @RequestPart("promotion")
            @Parameter(description = "Datos de la promoción en JSON")
            String promotionJson,
            @RequestPart(value = "image", required = false)
            @Parameter(description = "Imagen de la promoción (máximo 2MB)")
            MultipartFile image) {
        try {
            validateImageSize(image);
            HomePromotionsAdminDTO promotionDTO = objectMapper.readValue(promotionJson, HomePromotionsAdminDTO.class);
            HomePromotionsAdminDTO createdPromotion = manageService.createPromotion(promotionDTO, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Actualizar promoción",
        description = "Actualiza una promoción existente con imagen opcional"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promoción actualizada exitosamente"),
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
    public ResponseEntity<HomePromotionsAdminDTO> updatePromotion(
            @RequestPart("promotion")
            @Parameter(description = "Datos actualizados de la promoción")
            String promotionJson,
            @RequestPart(value = "image", required = false)
            @Parameter(description = "Nueva imagen (opcional)")
            MultipartFile image) {
        try {
            validateImageSize(image);
            HomePromotionsAdminDTO promotionDTO = objectMapper.readValue(promotionJson, HomePromotionsAdminDTO.class);
            HomePromotionsAdminDTO updatedPromotion = manageService.updatePromotion(promotionDTO, image);
            return ResponseEntity.ok(updatedPromotion);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Eliminar promoción",
        description = "Elimina una promoción"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Promoción eliminada exitosamente"),
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
            description = "Promoción no encontrada",
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
    public ResponseEntity<Void> deletePromotion(
            @PathVariable
            @Parameter(description = "ID de la promoción a eliminar")
            UUID id) {
        manageService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_PROMOTIONS + " Activar promoción",
        description = "Activa una promoción"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Promoción activada exitosamente"),
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
            description = "Promoción no encontrada",
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
    public ResponseEntity<HomePromotionsAdminDTO> activatePromotion(
            @PathVariable
            @Parameter(description = "ID de la promoción a activar")
            UUID id) {
        HomePromotionsAdminDTO activatedPromotion = manageService.activatePromotion(id);
        return ResponseEntity.ok(activatedPromotion);
    }

    private void validateImageSize(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            long sizeInBytes = image.getSize();
            if (sizeInBytes > MAX_IMAGE_SIZE) {
                double sizeInMB = sizeInBytes / (1024.0 * 1024.0);
                throw new IllegalArgumentException(
                    String.format("La imagen excede el tamaño máximo permitido de 2MB. Tamaño actual: %.2f MB", sizeInMB)
                );
            }
        }
    }
}

