package com.elsalvador.coopac.service.publicpages.mapper.about;

import com.elsalvador.coopac.dto.publicpage.about.AboutPageDTO;
import com.elsalvador.coopac.entity.about.*;
import com.elsalvador.coopac.entity.page.PageHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de About a DTOs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AboutMapper {

    /**
     * Mapea el header de About usando PageHeaders
     */
    public AboutPageDTO.HeaderDTO mapHeader(PageHeaders pageHeader) {
        return new AboutPageDTO.HeaderDTO(
                pageHeader.getTitleMain(),
                pageHeader.getSubtitle()
        );
    }

    /**
     * Mapea la sección de historia con sus eventos
     */
    public AboutPageDTO.HistoryDTO mapHistory(AboutHistorySection historySection, List<AboutTimelineEvents> timelineEvents) {
        var eventDtos = timelineEvents.stream()
                .map(this::mapTimelineEvent)
                .collect(Collectors.toList());

        return new AboutPageDTO.HistoryDTO(
                historySection.getTitle(),
                historySection.getSubtitle(),
                new AboutPageDTO.HighlightDTO(
                        historySection.getHighlightValue(),
                        historySection.getHighlightTitle(),
                        historySection.getHighlightNote()
                ),
                eventDtos
        );
    }

    /**
     * Mapea un evento individual del timeline
     */
    private AboutPageDTO.TimelineEventDTO mapTimelineEvent(AboutTimelineEvents event) {
        return new AboutPageDTO.TimelineEventDTO(
                event.getYearLabel(),
                event.getTitle(),
                event.getDescription(),
                event.getDisplayOrder()
        );
    }

    /**
     * Mapea la sección de misión y visión
     */
    public AboutPageDTO.MissionVisionDTO mapMissionVision(AboutMissionVision missionVision) {
        return new AboutPageDTO.MissionVisionDTO(
                new AboutPageDTO.MissionDTO(
                        missionVision.getMissionTitle(),
                        missionVision.getMissionText(),
                        missionVision.getMissionIcon()
                ),
                new AboutPageDTO.VisionDTO(
                        missionVision.getVisionTitle(),
                        missionVision.getVisionText(),
                        missionVision.getVisionIcon()
                )
        );
    }

    /**
     * Mapea la sección de valores con sus items
     */
    public AboutPageDTO.ValuesDTO mapValues(AboutValuesSection valuesSection, List<AboutValues> values) {
        var valueDtos = values.stream()
                .map(this::mapValueItem)
                .collect(Collectors.toList());

        return new AboutPageDTO.ValuesDTO(
                valuesSection.getTitle(),
                valuesSection.getSubtitle(),
                valueDtos
        );
    }

    /**
     * Mapea un valor individual
     */
    private AboutPageDTO.ValueItemDTO mapValueItem(AboutValues value) {
        return new AboutPageDTO.ValueItemDTO(
                value.getIcon(),
                value.getTitle(),
                value.getDescription(),
                value.getDisplayOrder()
        );
    }

    /**
     * Mapea la sección de impacto con sus métricas
     */
    public AboutPageDTO.ImpactDTO mapImpact(AboutImpactSection impactSection, List<AboutImpactMetrics> metrics) {
        var metricDtos = metrics.stream()
                .map(this::mapMetric)
                .collect(Collectors.toList());

        return new AboutPageDTO.ImpactDTO(
                impactSection.getTitle(),
                impactSection.getSubtitle(),
                metricDtos
        );
    }

    /**
     * Mapea una métrica individual
     */
    private AboutPageDTO.MetricDTO mapMetric(AboutImpactMetrics metric) {
        return new AboutPageDTO.MetricDTO(
                metric.getLabel(),
                metric.getValueText(),
                metric.getIcon(),
                metric.getDisplayOrder()
        );
    }

    /**
     * Mapea la sección del consejo con sus miembros
     */
    public AboutPageDTO.BoardDTO mapBoard(AboutBoardSection boardSection, List<AboutBoardMembers> members) {
        var memberDtos = members.stream()
                .map(this::mapBoardMember)
                .collect(Collectors.toList());

        return new AboutPageDTO.BoardDTO(
                boardSection.getTitle(),
                boardSection.getSubtitle(),
                memberDtos
        );
    }

    /**
     * Mapea un miembro del consejo individual
     */
    private AboutPageDTO.BoardMemberDTO mapBoardMember(AboutBoardMembers member) {
        return new AboutPageDTO.BoardMemberDTO(
                member.getFullName(),
                member.getPosition(),
                member.getPhotoUrl(),
                member.getBio(),
                member.getLinkedinUrl(),
                member.getEmail(),
                member.getDisplayOrder()
        );
    }
}
