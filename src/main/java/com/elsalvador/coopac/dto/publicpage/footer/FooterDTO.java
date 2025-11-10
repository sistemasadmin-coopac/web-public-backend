package com.elsalvador.coopac.dto.publicpage.footer;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Datos completos del footer del sitio")
public record FooterDTO(
    @Schema(description = "Columnas de navegacion del footer")
    List<FooterColumnDTO> columns,
    @Schema(description = "Informacion de la empresa y enlaces sociales")
    FooterInfoDTO info
) {

    @Schema(description = "Columna de navegacion del footer")
    public record FooterColumnDTO(
        @Schema(description = "Titulo de la columna", example = "Productos")
        String title,
        @Schema(description = "Enlaces en esta columna")
        List<FooterLinkDTO> links,
        @Schema(description = "Orden de aparicion de la columna")
        Integer order
    ) {}

    @Schema(description = "Link del footer")
    public record FooterLinkDTO(
        @Schema(description = "Texto del enlace", example = "Ahorros")
        String label,
        @Schema(description = "URL del enlace")
        String url,
        @Schema(description = "Indica si el enlace abre en nueva ventana")
        Boolean external,
        @Schema(description = "Orden de aparicion del enlace")
        Integer order
    ) {}

    @Schema(description = "Informacion de la empresa en el footer")
    public record FooterInfoDTO(
        @Schema(description = "Nombre de la empresa", example = "COOPAC El Salvador")
        String companyName,
        @Schema(description = "URL del logo")
        String logoUrl,
        @Schema(description = "Texto alternativo del logo")
        String logoAlt,
        @Schema(description = "Informacion de contacto")
        ContactInfoDTO contact,
        @Schema(description = "Enlaces a redes sociales")
        SocialLinksDTO social,
        @Schema(description = "Texto de copyright", example = "© 2024 COOPAC. Todos los derechos reservados.")
        String copyrightText
    ) {}

    @Schema(description = "Informacion de contacto del footer")
    public record ContactInfoDTO(
        @Schema(description = "Numero de telefono", example = "+503 2555-1234")
        String phone,
        @Schema(description = "Email de contacto", example = "info@coopac.com")
        String email,
        @Schema(description = "Direccion de la empresa")
        String address
    ) {}

    @Schema(description = "Enlaces a redes sociales")
    public record SocialLinksDTO(
        @Schema(description = "URL de Facebook")
        String facebook,
        @Schema(description = "URL de Instagram")
        String instagram,
        @Schema(description = "URL de LinkedIn")
        String linkedin,
        @Schema(description = "URL de Twitter/X")
        String twitter
    ) {}
}


