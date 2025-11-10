package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.header.UpdatePageHeaderService;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Headers.TAG_NAME,
    description = SwaggerTags.Headers.TAG_DESCRIPTION
)
public class UpdatePageHeaderController {

    private final UpdatePageHeaderService updatePageHeaderService;

    @PutMapping("/page-headers")
    @Operation(
        summary = SwaggerTags.Headers.EMOJI_PAGE_HEADERS + " Actualizar header de página",
        description = "Actualiza el header (título, subtítulo, descripción) de una página"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Header actualizado exitosamente",
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
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<PageHeaderAdminDTO> updatePageHeader(
            @Valid @RequestBody PageHeaderAdminDTO pageHeaderDTO) {
        PageHeaderAdminDTO updatedHeader = updatePageHeaderService.updatePageHeader(pageHeaderDTO);
        return ResponseEntity.ok(updatedHeader);
    }
}

