package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.footer.FooterDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.GetDataFooterPageService;
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
@RequestMapping("/api/footer")
@RequiredArgsConstructor
@Slf4j
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataFooterPageController {

    private final GetDataFooterPageService getDataFooterPageService;

    @GetMapping
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_GENERAL + " Obtener datos del footer",
        description = "Obtiene la informacion del footer incluyendo columnas de navegacion, informacion de contacto y enlaces a redes sociales"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos del footer obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public FooterDTO getFooter() {
        log.info("Solicitados datos del footer");
        return getDataFooterPageService.getFooterData();
    }
}


