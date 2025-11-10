package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.about.AboutPageDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.GetDataAboutPageService;
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
@RequestMapping("/api/about")
@RequiredArgsConstructor
@Slf4j
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataAboutPageController {

    private final GetDataAboutPageService getDataAboutPageServiceImpl;

    @GetMapping("/page")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_ABOUT + " Obtener datos de pagina About",
        description = "Obtiene la informacion completa de la pagina About incluyendo historia, mision, vision, valores, impacto y junta directiva"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la pagina About obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public AboutPageDTO getAboutPage() {
        log.info("Solicitada pagina About");
        return getDataAboutPageServiceImpl.getAboutPageData();
    }
}


