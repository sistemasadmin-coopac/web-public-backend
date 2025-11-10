package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.navigation.NavigationDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.GetDataNavigationPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/navigation")
@RequiredArgsConstructor
@Slf4j
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataNavigationPageController {

    private final GetDataNavigationPageService getDataNavigationPageService;

    @GetMapping("/main")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_GENERAL + " Obtener navegación principal",
        description = "Retorna la estructura completa de navegación incluyendo menús, marca y accesos directos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Navegación obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public NavigationDTO getMainNavigation() {
        log.info("Solicitada navegación principal");
        return getDataNavigationPageService.getMainNavigation();
    }
}
