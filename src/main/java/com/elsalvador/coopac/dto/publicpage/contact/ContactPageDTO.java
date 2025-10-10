package com.elsalvador.coopac.dto.publicpage.contact;

import java.math.BigDecimal;
import java.util.List;

public record ContactPageDTO(
    HeaderDTO header,
    SectionsDTO sections
) {

    public record HeaderDTO(
        String titleMain,
        String subtitle
    ) {}

    public record SectionsDTO(
        ContactInfoDTO contactInfo,
        ScheduleDTO schedule,
        LocationDTO location
    ) {}

    public record ContactInfoDTO(
        String title,
        List<ContactItemDTO> items
    ) {}

    public record ContactItemDTO(
        String type,
        String icon,
        String label,
        String value,
        String description,
        Integer order
    ) {}

    public record ScheduleDTO(
        String title,
        List<ScheduleItemDTO> items,
        String note
    ) {}

    public record ScheduleItemDTO(
        String label,
        String open,
        String close,
        Boolean isClosed,
        Integer order
    ) {}

    public record LocationDTO(
        String title,
        String subtitle,
        List<LocationPlaceDTO> places
    ) {}

    public record LocationPlaceDTO(
        String name,
        String address,
        String phone,
        String whatsapp,
        String scheduleLabel,
        MapDTO map,
        List<ActionDTO> actions
    ) {}

    public record MapDTO(
        BigDecimal lat,
        BigDecimal lng,
        Integer zoom
    ) {}

    public record ActionDTO(
        String label,
        String type,
        String value,
        Boolean primary,
        Integer order
    ) {}
}
