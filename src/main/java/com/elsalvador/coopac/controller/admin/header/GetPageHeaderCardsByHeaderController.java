package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.header.GetPageHeaderCardsByHeaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Headers.TAG_NAME,
    description = SwaggerTags.Headers.TAG_DESCRIPTION
)
public class GetPageHeaderCardsByHeaderController {

    private final GetPageHeaderCardsByHeaderService getPageHeaderCardsByHeaderService;

    @GetMapping("/page-header-cards/header/{headerId}")
    @Operation(
        summary = SwaggerTags.Headers.EMOJI_CARDS + " Obtener tarjetas del header",
        description = "Obtiene todas las tarjetas/cards asociadas a un header específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Tarjetas obtenidas exitosamente",
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
            responseCode = "404",
            description = "Header no encontrado",
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
    public ResponseEntity<List<PageHeaderCardsAdminDTO>> getPageHeaderCardsByHeader(
            @PathVariable
            @Parameter(description = "ID del header")
            String headerId) {
        List<PageHeaderCardsAdminDTO> cards = getPageHeaderCardsByHeaderService.getPageHeaderCardsByHeader(headerId);
        return ResponseEntity.ok(cards);
    }
}

