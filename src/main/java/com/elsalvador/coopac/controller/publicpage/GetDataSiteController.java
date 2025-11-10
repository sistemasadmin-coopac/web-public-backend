package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.site.SiteSettingsDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/site")
@Slf4j
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataSiteController {

    @GetMapping("/settings")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_GENERAL + " Obtener configuración del sitio",
        description = "Retorna la configuración completa incluyendo empresa, contacto, redes sociales y timestamp de última actualización"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuración obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public SiteSettingsDTO getSiteSettings() {
        return new SiteSettingsDTO(
            // Company
            new SiteSettingsDTO.CompanyDTO(
                "COOPERATIVA DE AHORRO Y CREDITO \"EL SALVADOR\" LTDA.",
                new SiteSettingsDTO.LogoDTO(
                    "https://cdn.example.com/brand/coopac-el-salvador-logo.svg",
                    "COOPAC El Salvador"
                )
            ),

            // Contact
            new SiteSettingsDTO.ContactDTO(
                "044-544011",
                null,
                new SiteSettingsDTO.WhatsAppDTO(
                    "970003173",
                    "https://wa.me/51970003173"
                ),
                "info@coopac-elsalvador.pe",
                null,
                new SiteSettingsDTO.AddressDTO(
                    "Av. Principal s/n",
                    null,
                    "Paiján",
                    "La Libertad",
                    "Perú"
                ),
                "https://maps.google.com/?q=Paiján%2C+La+Libertad"
            ),

            // Social
            new SiteSettingsDTO.SocialDTO(
                "https://facebook.com/coopac-elsalvador",
                "https://instagram.com/coopac-elsalvador",
                "https://linkedin.com/company/coopac-elsalvador",
                null
            ),

            // Updated At
            ZonedDateTime.parse("2025-08-28T15:22:55Z")
        );
    }
}
