package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.join.JoinPageDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.GetDataJoinPageService;
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
@RequestMapping("/api/join")
@RequiredArgsConstructor
@Slf4j
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataJoinPageController {

    private final GetDataJoinPageService getDataJoinPageService;

    @GetMapping("/page")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_JOIN + " Obtener datos de pagina Join",
        description = "Obtiene la informacion completa de la pagina Join incluyendo beneficios, costos, requisitos y pasos para asociarse"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la pagina Join obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public JoinPageDTO getJoinPage() {
        log.info("Solicitada pagina Join/Asociate Ya");
        return getDataJoinPageService.getJoinPageData();
    }
}



