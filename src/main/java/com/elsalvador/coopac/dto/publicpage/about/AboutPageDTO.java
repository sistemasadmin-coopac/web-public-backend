package com.elsalvador.coopac.dto.publicpage.about;

import java.util.List;

public record AboutPageDTO(
    HeaderDTO header,
    SectionsDTO sections
) {

    public record HeaderDTO(
        String titleMain,
        String subtitle
    ) {}

    public record SectionsDTO(
        HistoryDTO history,
        MissionVisionDTO missionVision,
        ValuesDTO values,
        ImpactDTO impact,
        BoardDTO board
    ) {}

    public record HistoryDTO(
        String title,
        String subtitle,
        HighlightDTO highlight,
        List<TimelineEventDTO> events
    ) {}

    public record HighlightDTO(
        String value,
        String title,
        String note
    ) {}

    public record TimelineEventDTO(
        String yearLabel,
        String title,
        String description,
        Integer order
    ) {}

    public record MissionVisionDTO(
        MissionDTO mission,
        VisionDTO vision
    ) {}

    public record MissionDTO(
        String title,
        String text,
        String icon
    ) {}

    public record VisionDTO(
        String title,
        String text,
        String icon
    ) {}

    public record ValuesDTO(
        String title,
        String subtitle,
        List<ValueItemDTO> items
    ) {}

    public record ValueItemDTO(
        String icon,
        String title,
        String description,
        Integer order
    ) {}

    public record ImpactDTO(
        String title,
        String subtitle,
        List<MetricDTO> items
    ) {}

    public record MetricDTO(
        String label,
        String valueText,
        String icon,
        Integer order
    ) {}

    public record BoardDTO(
        String title,
        String subtitle,
        List<BoardMemberDTO> members
    ) {}

    public record BoardMemberDTO(
        String fullName,
        String position,
        String photoBase64,
        String bio,
        String linkedinUrl,
        String email,
        Integer order
    ) {}
}
