package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.GetDataHomePageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataHomePageController {

    private final GetDataHomePageService getDataHomePageService;

    @GetMapping("/home")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_HOME + " Obtener datos de pagina Home",
        description = "Obtiene la informacion completa de la pagina Home incluyendo promociones, bloques CTA y estadisticas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la pagina Home obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public HomePageDTO getHomePage() {
        return getDataHomePageService.getHomePageData();
    }
}


