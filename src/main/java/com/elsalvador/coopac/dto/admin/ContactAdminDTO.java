package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

/**
 * DTOs para administración de contacto
 */
public class ContactAdminDTO {

    /**
     * DTO para respuesta completa de contacto
     */
    @Schema(description = "Respuesta completa con todos los datos de contacto")
    public record ContactPageResponseDTO(
            @Schema(description = "Header o encabezado de la página de contacto")
            ContactHeaderDTO header,
            @Schema(description = "Secciones principales de contacto")
            ContactSectionsDTO sections
    ) {}

    /**
     * DTO para el header de contacto
     */
    @Schema(description = "Header de la página de contacto")
    public record ContactHeaderDTO(
            @Schema(description = "Título principal", example = "Ponte en Contacto")
            String titleMain,
            @Schema(description = "Subtítulo", example = "Estamos aquí para ayudarte")
            String subtitle
    ) {}

    /**
     * DTO para las secciones de contacto
     */
    @Schema(description = "Secciones principales de la página de contacto")
    public record ContactSectionsDTO(
            @Schema(description = "Información de canales de contacto")
            ContactInfoSectionDTO contactInfo,
            @Schema(description = "Horarios de atención")
            ContactScheduleSectionDTO schedule,
            @Schema(description = "Ubicaciones")
            ContactLocationSectionDTO location
    ) {}

    // DTOs para información de contacto
    @Schema(description = "Sección de información de contacto")
    public record ContactInfoSectionDTO(
            @Schema(description = "Título de la sección", example = "Canales de Contacto")
            String title,
            @Schema(description = "Lista de canales de contacto disponibles")
            List<ContactChannelItemDTO> items
    ) {}

    @Schema(description = "Item de canal de contacto")
    public record ContactChannelItemDTO(
            @Schema(description = "ID único del canal")
            UUID id,
            @Schema(description = "Tipo de canal", example = "email, phone, whatsapp")
            String type,
            @Schema(description = "Icono del canal", example = "envelope")
            String icon,
            @Schema(description = "Etiqueta visible", example = "Correo Electrónico")
            String label,
            @Schema(description = "Valor/URL del canal", example = "contacto@ejemplo.com")
            String value,
            @Schema(description = "Descripción adicional")
            String description,
            @Schema(description = "Orden de visualización")
            Integer order,
            @Schema(description = "Usar valor global o personalizado")
            Boolean useGlobalValue,
            @Schema(description = "Valor personalizado si no se usa el global")
            String customValue,
            @Schema(description = "Si el canal está activo")
            Boolean isActive
    ) {}

    @Schema(description = "DTO para actualizar un canal de contacto")
    public record UpdateContactChannelDTO(
            @Schema(description = "Icono del canal")
            String icon,
            @Schema(description = "Etiqueta del canal")
            String label,
            @Schema(description = "Descripción del canal")
            String description,
            @Schema(description = "Usar valor global")
            Boolean useGlobalValue,
            @Schema(description = "Valor personalizado")
            String customValue,
            @Schema(description = "Orden de visualización")
            Integer displayOrder,
            @Schema(description = "Estado activo del canal")
            Boolean isActive
    ) {}

    // DTOs para horarios
    @Schema(description = "Sección de horarios de contacto")
    public record ContactScheduleSectionDTO(
            @Schema(description = "Título de la sección", example = "Horarios de Atención")
            String title,
            @Schema(description = "Lista de horarios")
            List<ContactScheduleItemDTO> items,
            @Schema(description = "Nota adicional sobre horarios")
            String note
    ) {}

    @Schema(description = "Item de horario")
    public record ContactScheduleItemDTO(
            @Schema(description = "ID único del horario")
            UUID id,
            @Schema(description = "Etiqueta del día/horario", example = "Lunes a Viernes")
            String label,
            @Schema(description = "Hora de apertura", example = "09:00")
            String open,
            @Schema(description = "Hora de cierre", example = "18:00")
            String close,
            @Schema(description = "Si está cerrado en este período")
            Boolean isClosed,
            @Schema(description = "Orden de visualización")
            Integer order,
            @Schema(description = "Nota adicional del horario")
            String note,
            @Schema(description = "Si el horario está activo")
            Boolean isActive
    ) {}

    @Schema(description = "DTO para actualizar horario de contacto")
    public record UpdateContactScheduleDTO(
            @NotNull(message = "Hora de apertura es requerida")
            @Schema(description = "Hora de apertura", example = "09:00")
            LocalTime openTime,
            @NotNull(message = "Hora de cierre es requerida")
            @Schema(description = "Hora de cierre", example = "18:00")
            LocalTime closeTime
    ) {}

    // DTOs para ubicaciones
    @Schema(description = "Sección de ubicaciones")
    public record ContactLocationSectionDTO(
            @Schema(description = "Título de la sección", example = "Nuestras Ubicaciones")
            String title,
            @Schema(description = "Subtítulo de la sección")
            String subtitle,
            @Schema(description = "Lista de ubicaciones")
            List<ContactLocationPlaceDTO> places
    ) {}

    @Schema(description = "Ubicación de contacto")
    public record ContactLocationPlaceDTO(
            @Schema(description = "ID único de la ubicación")
            UUID id,
            @Schema(description = "Nombre de la ubicación", example = "Oficina Central")
            String name,
            @Schema(description = "Dirección completa")
            String address,
            @Schema(description = "Teléfono de la ubicación")
            String phone,
            @Schema(description = "Número de WhatsApp")
            String whatsapp,
            @Schema(description = "Etiqueta del horario")
            String scheduleLabel,
            @Schema(description = "Datos del mapa")
            ContactMapDTO map,
            @Schema(description = "Acciones disponibles en la ubicación")
            List<ContactActionDTO> actions,
            @Schema(description = "Si la ubicación está activa")
            Boolean isActive
    ) {}

    @Schema(description = "Datos del mapa para ubicación")
    public record ContactMapDTO(
            @Schema(description = "Latitud", example = "13.6929")
            BigDecimal lat,
            @Schema(description = "Longitud", example = "-89.2182")
            BigDecimal lng,
            @Schema(description = "Nivel de zoom del mapa", example = "15")
            Integer zoom
    ) {}

    @Schema(description = "Acción disponible en una ubicación")
    public record ContactActionDTO(
            @Schema(description = "Etiqueta de la acción", example = "Llamar")
            String label,
            @Schema(description = "Tipo de acción", example = "call, message, email")
            String type,
            @Schema(description = "Valor de la acción")
            String value,
            @Schema(description = "Si es la acción principal")
            Boolean primary,
            @Schema(description = "Orden de visualización")
            Integer order
    ) {}

    @Schema(description = "DTO para actualizar ubicación de contacto")
    public record UpdateContactLocationDTO(
            @Schema(description = "Nombre de la ubicación")
            String name,
            @Schema(description = "Dirección de la ubicación")
            String address,
            @Schema(description = "Teléfono personalizado")
            String phoneOverride,
            @Schema(description = "WhatsApp personalizado")
            String whatsappOverride,
            @Schema(description = "Etiqueta del horario")
            String scheduleLabel,
            @Schema(description = "URL del mapa embebido de Google Maps")
            String googleMapsEmbedUrl,
            @Schema(description = "Latitud", example = "13.6929")
            BigDecimal latitude,
            @Schema(description = "Longitud", example = "-89.2182")
            BigDecimal longitude,
            @Schema(description = "Orden de visualización")
            Integer displayOrder,
            @Schema(description = "Si la ubicación está activa")
            Boolean isActive
    ) {}
}
