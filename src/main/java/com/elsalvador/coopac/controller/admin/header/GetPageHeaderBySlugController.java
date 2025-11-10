package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.header.GetPageHeaderBySlugService;
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

@RestController
@RequestMapping("/api/admin/page-headers")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Headers.TAG_NAME,
    description = SwaggerTags.Headers.TAG_DESCRIPTION
)
public class GetPageHeaderBySlugController {

    private final GetPageHeaderBySlugService getPageHeaderBySlugService;

    @GetMapping("/slug/{pageSlug}")
    @Operation(
        summary = SwaggerTags.Headers.EMOJI_PAGE_HEADERS + " Obtener header por slug",
        description = "Obtiene el header de una página específica usando su slug"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Header obtenido exitosamente",
            content = @Content(mediaType = "application/json")
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
    public ResponseEntity<PageHeaderAdminDTO> getPageHeaderBySlug(
            @PathVariable
            @Parameter(description = "Slug de la página (ej: about, contact, home)")
            String pageSlug) {
        PageHeaderAdminDTO header = getPageHeaderBySlugService.getPageHeaderBySlug(pageSlug);
        return ResponseEntity.ok(header);
    }
}

