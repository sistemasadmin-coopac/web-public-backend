package com.elsalvador.coopac.dto.publicpage.contact;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Datos completos de la pagina Contact")
public record ContactPageDTO(
    @Schema(description = "Encabezado de la pagina")
    HeaderDTO header,
    @Schema(description = "Secciones de informacion de contacto")
    SectionsDTO sections
) {

    @Schema(description = "Header de la pagina")
    public record HeaderDTO(
        @Schema(description = "Titulo principal")
        String titleMain,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle
    ) {}

    @Schema(description = "Secciones de contacto")
    public record SectionsDTO(
        @Schema(description = "Informacion de contacto")
        ContactInfoDTO contactInfo,
        @Schema(description = "Horario de atencion")
        ScheduleDTO schedule,
        @Schema(description = "Ubicaciones")
        LocationDTO location
    ) {}

    @Schema(description = "Informacion de contacto")
    public record ContactInfoDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Items de contacto")
        List<ContactItemDTO> items
    ) {}

    @Schema(description = "Item de contacto")
    public record ContactItemDTO(
        @Schema(description = "Tipo de contacto", example = "phone")
        String type,
        @Schema(description = "Icono")
        String icon,
        @Schema(description = "Etiqueta", example = "Telefono")
        String label,
        @Schema(description = "Valor o contenido")
        String value,
        @Schema(description = "Descripcion adicional")
        String description,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Horario de atencion")
    public record ScheduleDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Items de horario")
        List<ScheduleItemDTO> items,
        @Schema(description = "Nota adicional sobre horarios")
        String note
    ) {}

    @Schema(description = "Item de horario")
    public record ScheduleItemDTO(
        @Schema(description = "Etiqueta del dia o periodo", example = "Lunes a Viernes")
        String label,
        @Schema(description = "Hora de apertura", example = "08:00")
        String open,
        @Schema(description = "Hora de cierre", example = "17:00")
        String close,
        @Schema(description = "Indica si esta cerrado")
        Boolean isClosed,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Ubicaciones")
    public record LocationDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Lista de ubicaciones")
        List<LocationPlaceDTO> places
    ) {}

    @Schema(description = "Ubicacion especifica")
    public record LocationPlaceDTO(
        @Schema(description = "Nombre de la ubicacion")
        String name,
        @Schema(description = "Direccion completa")
        String address,
        @Schema(description = "Numero de telefono")
        String phone,
        @Schema(description = "Numero de WhatsApp")
        String whatsapp,
        @Schema(description = "Etiqueta de horario")
        String scheduleLabel,
        @Schema(description = "Datos del mapa")
        MapDTO map,
        @Schema(description = "Acciones disponibles")
        List<ActionDTO> actions
    ) {}

    @Schema(description = "Datos de mapa")
    public record MapDTO(
        @Schema(description = "Latitud", example = "13.69")
        BigDecimal lat,
        @Schema(description = "Longitud", example = "-89.22")
        BigDecimal lng,
        @Schema(description = "Nivel de zoom del mapa")
        Integer zoom
    ) {}

    @Schema(description = "Accion disponible")
    public record ActionDTO(
        @Schema(description = "Etiqueta de la accion")
        String label,
        @Schema(description = "Tipo de accion", example = "link")
        String type,
        @Schema(description = "Valor de la accion")
        String value,
        @Schema(description = "Indica si es accion primaria")
        Boolean primary,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}
}
