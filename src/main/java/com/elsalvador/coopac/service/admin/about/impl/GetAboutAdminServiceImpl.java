package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.*;
import com.elsalvador.coopac.repository.about.*;
import com.elsalvador.coopac.service.admin.about.GetAboutAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para obtener datos completos de la página About
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetAboutAdminServiceImpl implements GetAboutAdminService {

    private final AboutMissionVisionRepository missionVisionRepository;
    private final AboutValuesSectionRepository valuesSectionRepository;
    private final AboutValuesRepository valuesRepository;
    private final AboutHistorySectionRepository historySectionRepository;
    private final AboutTimelineEventsRepository timelineEventsRepository;
    private final AboutImpactSectionRepository impactSectionRepository;
    private final AboutImpactMetricsRepository impactMetricsRepository;
    private final AboutBoardSectionRepository boardSectionRepository;
    private final AboutBoardMembersRepository boardMembersRepository;

    @Override
    public AboutAdminDTO.AboutPageResponseDTO getAboutCompleteData() {
        log.info("Obteniendo datos completos de la página About para administración");

        return AboutAdminDTO.AboutPageResponseDTO.builder()
                .missionVision(getMissionVision())
                .valuesSection(getValuesSection())
                .values(getValues())
                .historySection(getHistorySection())
                .timeline(getTimelineEvents())
                .impactSection(getImpactSection())
                .impactMetrics(getImpactMetrics())
                .boardSection(getBoardSection())
                .boardMembers(getBoardMembers())
                .build();
    }

    /**
     * Obtiene la configuración de misión y visión
     */
    private AboutAdminDTO.AboutMissionVisionDTO getMissionVision() {
        return missionVisionRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .map(this::mapMissionVisionToDTO)
                .orElse(null);
    }

    /**
     * Obtiene la configuración de la sección de valores
     */
    private AboutAdminDTO.AboutValuesSectionDTO getValuesSection() {
        return valuesSectionRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .map(this::mapValuesSectionToDTO)
                .orElse(null);
    }

    /**
     * Obtiene todos los valores activos
     */
    private List<AboutAdminDTO.AboutValueDTO> getValues() {
        List<AboutValues> values = valuesRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
        return values.stream()
                .map(this::mapValueToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la configuración de la sección de historia
     */
    private AboutAdminDTO.AboutHistorySectionDTO getHistorySection() {
        return historySectionRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .map(this::mapHistorySectionToDTO)
                .orElse(null);
    }

    /**
     * Obtiene todos los eventos del timeline activos
     */
    private List<AboutAdminDTO.AboutTimelineEventDTO> getTimelineEvents() {
        List<AboutTimelineEvents> events = timelineEventsRepository.findByIsActiveTrueOrderByYearLabelAscDisplayOrderAsc();
        return events.stream()
                .map(this::mapTimelineEventToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la configuración de la sección de impacto
     */
    private AboutAdminDTO.AboutImpactSectionDTO getImpactSection() {
        return impactSectionRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .map(this::mapImpactSectionToDTO)
                .orElse(null);
    }

    /**
     * Obtiene todas las métricas de impacto activas
     */
    private List<AboutAdminDTO.AboutImpactMetricDTO> getImpactMetrics() {
        List<AboutImpactMetrics> metrics = impactMetricsRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
        return metrics.stream()
                .map(this::mapImpactMetricToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la configuración de la sección de junta directiva
     */
    private AboutAdminDTO.AboutBoardSectionDTO getBoardSection() {
        return boardSectionRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .map(this::mapBoardSectionToDTO)
                .orElse(null);
    }

    /**
     * Obtiene todos los miembros de junta directiva activos
     */
    private List<AboutAdminDTO.AboutBoardMemberDTO> getBoardMembers() {
        List<AboutBoardMembers> members = boardMembersRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
        return members.stream()
                .map(this::mapBoardMemberToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea entidad MissionVision a DTO
     */
    private AboutAdminDTO.AboutMissionVisionDTO mapMissionVisionToDTO(AboutMissionVision entity) {
        return AboutAdminDTO.AboutMissionVisionDTO.builder()
                .id(entity.getId())
                .missionTitle(entity.getMissionTitle())
                .missionText(entity.getMissionText())
                .visionTitle(entity.getVisionTitle())
                .visionText(entity.getVisionText())
                .isActive(entity.getIsActive())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Mapea entidad ValuesSection a DTO
     */
    private AboutAdminDTO.AboutValuesSectionDTO mapValuesSectionToDTO(AboutValuesSection entity) {
        return AboutAdminDTO.AboutValuesSectionDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .isActive(entity.getIsActive())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Mapea entidad Value a DTO
     */
    private AboutAdminDTO.AboutValueDTO mapValueToDTO(AboutValues entity) {
        return AboutAdminDTO.AboutValueDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .build();
    }

    /**
     * Mapea entidad HistorySection a DTO
     */
    private AboutAdminDTO.AboutHistorySectionDTO mapHistorySectionToDTO(AboutHistorySection entity) {
        return AboutAdminDTO.AboutHistorySectionDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .highlightValue(entity.getHighlightValue())
                .highlightTitle(entity.getHighlightTitle())
                .highlightNote(entity.getHighlightNote())
                .isActive(entity.getIsActive())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Mapea entidad TimelineEvent a DTO
     */
    private AboutAdminDTO.AboutTimelineEventDTO mapTimelineEventToDTO(AboutTimelineEvents entity) {
        return AboutAdminDTO.AboutTimelineEventDTO.builder()
                .id(entity.getId())
                .yearLabel(entity.getYearLabel())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .build();
    }

    /**
     * Mapea entidad ImpactSection a DTO
     */
    private AboutAdminDTO.AboutImpactSectionDTO mapImpactSectionToDTO(AboutImpactSection entity) {
        return AboutAdminDTO.AboutImpactSectionDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .isActive(entity.getIsActive())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Mapea entidad ImpactMetric a DTO
     */
    private AboutAdminDTO.AboutImpactMetricDTO mapImpactMetricToDTO(AboutImpactMetrics entity) {
        return AboutAdminDTO.AboutImpactMetricDTO.builder()
                .id(entity.getId())
                .label(entity.getLabel())
                .valueText(entity.getValueText())
                .footnote(entity.getFootnote())
                .icon(entity.getIcon())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .build();
    }

    /**
     * Mapea entidad BoardSection a DTO
     */
    private AboutAdminDTO.AboutBoardSectionDTO mapBoardSectionToDTO(AboutBoardSection entity) {
        return AboutAdminDTO.AboutBoardSectionDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .isActive(entity.getIsActive())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Mapea entidad BoardMember a DTO
     */
    private AboutAdminDTO.AboutBoardMemberDTO mapBoardMemberToDTO(AboutBoardMembers entity) {
        return AboutAdminDTO.AboutBoardMemberDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .position(entity.getPosition())
                .photoUrl(entity.getPhotoUrl())
                .bio(entity.getBio())
                .linkedinUrl(entity.getLinkedinUrl())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .displayOrder(entity.getDisplayOrder())
                .isActive(entity.getIsActive())
                .build();
    }
}
