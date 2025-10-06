package com.elsalvador.coopac.dto.admin;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * DTOs para administración de contacto
 */
public class ContactAdminDTO {

    /**
     * DTO para respuesta completa de contacto
     */
    public record ContactPageResponseDTO(
            ContactHeaderDTO header,
            ContactSectionsDTO sections
    ) {}

    /**
     * DTO para el header de contacto
     */
    public record ContactHeaderDTO(
            String titleMain,
            String subtitle
    ) {}

    /**
     * DTO para las secciones de contacto
     */
    public record ContactSectionsDTO(
            ContactInfoSectionDTO contactInfo,
            ContactScheduleSectionDTO schedule,
            ContactLocationSectionDTO location
    ) {}

    // DTOs para información de contacto
    public record ContactInfoSectionDTO(
            String title,
            List<ContactChannelItemDTO> items
    ) {}

    public record ContactChannelItemDTO(
            UUID id,
            String type,
            String icon,
            String label,
            String value,
            String description,
            Integer order,
            Boolean useGlobalValue,
            String customValue,
            Boolean isActive
    ) {}

    public record UpdateContactChannelDTO(
            String icon,
            String label,
            String description,
            Boolean useGlobalValue,
            String customValue,
            Integer displayOrder,
            Boolean isActive
    ) {}

    // DTOs para horarios
    public record ContactScheduleSectionDTO(
            String title,
            List<ContactScheduleItemDTO> items,
            String note
    ) {}

    public record ContactScheduleItemDTO(
            UUID id,
            String label,
            String open,
            String close,
            Boolean isClosed,
            Integer order,
            String note,
            Boolean isActive
    ) {}

    public record UpdateContactScheduleDTO(
            String label,
            LocalTime openTime,
            LocalTime closeTime,
            Boolean isClosed,
            Integer displayOrder,
            String note,
            Boolean isActive
    ) {}

    // DTOs para ubicaciones
    public record ContactLocationSectionDTO(
            String title,
            String subtitle,
            List<ContactLocationPlaceDTO> places
    ) {}

    public record ContactLocationPlaceDTO(
            UUID id,
            String name,
            String address,
            String phone,
            String whatsapp,
            String scheduleLabel,
            ContactMapDTO map,
            List<ContactActionDTO> actions,
            Boolean isActive
    ) {}

    public record ContactMapDTO(
            BigDecimal lat,
            BigDecimal lng,
            Integer zoom
    ) {}

    public record ContactActionDTO(
            String label,
            String type,
            String value,
            Boolean primary,
            Integer order
    ) {}

    public record UpdateContactLocationDTO(
            String name,
            String address,
            String phoneOverride,
            String whatsappOverride,
            String scheduleLabel,
            String googleMapsEmbedUrl,
            BigDecimal latitude,
            BigDecimal longitude,
            Integer displayOrder,
            Boolean isActive
    ) {}
}
