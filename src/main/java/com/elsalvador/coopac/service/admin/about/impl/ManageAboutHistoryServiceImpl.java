package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.AboutHistorySection;
import com.elsalvador.coopac.entity.about.AboutTimelineEvents;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.about.AboutHistorySectionRepository;
import com.elsalvador.coopac.repository.about.AboutTimelineEventsRepository;
import com.elsalvador.coopac.service.admin.about.ManageAboutHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar la historia y timeline de eventos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageAboutHistoryServiceImpl implements ManageAboutHistoryService {

    private final AboutTimelineEventsRepository timelineEventsRepository;
    private final AboutHistorySectionRepository historySectionRepository;

    @Override
    @Transactional
    public AboutAdminDTO.AboutTimelineEventDTO createTimelineEvent(AboutAdminDTO.AboutTimelineEventDTO dto) {
        log.info("Creando nuevo evento del timeline");

        // Obtener el siguiente displayOrder si no se especifica
        Integer displayOrder = dto.getDisplayOrder();
        if (displayOrder == null || displayOrder == 0) {
            displayOrder = timelineEventsRepository.findMaxDisplayOrder() + 1;
        }

        AboutTimelineEvents newEvent = AboutTimelineEvents.builder()
                .yearLabel(dto.getYearLabel())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .displayOrder(displayOrder)
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();

        AboutTimelineEvents savedEvent = timelineEventsRepository.save(newEvent);
        log.info("Evento del timeline creado exitosamente con ID: {}", savedEvent.getId());

        return mapEventToDTO(savedEvent);
    }

    @Override
    @Transactional
    public AboutAdminDTO.AboutTimelineEventDTO updateTimelineEvent(UUID id, AboutAdminDTO.AboutTimelineEventDTO dto) {
        log.info("Actualizando evento del timeline con ID: {}", id);

        AboutTimelineEvents existingEvent = timelineEventsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento del timeline no encontrado con ID: " + id));

        existingEvent.setYearLabel(dto.getYearLabel());
        existingEvent.setTitle(dto.getTitle());
        existingEvent.setDescription(dto.getDescription());
        existingEvent.setDisplayOrder(dto.getDisplayOrder());
        existingEvent.setIsActive(dto.getIsActive());

        AboutTimelineEvents updatedEvent = timelineEventsRepository.save(existingEvent);
        log.info("Evento del timeline actualizado exitosamente");

        return mapEventToDTO(updatedEvent);
    }

    @Override
    @Transactional
    public void deleteTimelineEvent(UUID id) {
        log.info("Eliminando evento del timeline con ID: {}", id);

        if (!timelineEventsRepository.existsById(id)) {
            throw new EntityNotFoundException("Evento del timeline no encontrado con ID: " + id);
        }

        timelineEventsRepository.deleteById(id);
        log.info("Evento del timeline eliminado exitosamente");
    }

    @Override
    @Transactional
    public AboutAdminDTO.AboutHistorySectionDTO updateHistorySection(AboutAdminDTO.AboutHistorySectionDTO dto) {
        log.info("Actualizando configuración de la sección de historia");

        AboutHistorySection section = historySectionRepository.findFirstByOrderByUpdatedAtDesc()
                .orElse(createDefaultHistorySection());

        section.setTitle(dto.getTitle());
        section.setSubtitle(dto.getSubtitle());
        section.setIsActive(dto.getIsActive());

        AboutHistorySection updatedSection = historySectionRepository.save(section);
        log.info("Configuración de la sección de historia actualizada exitosamente");

        return mapSectionToDTO(updatedSection);
    }

    private AboutHistorySection createDefaultHistorySection() {
        return AboutHistorySection.builder()
                .title("Nuestra Historia")
                .subtitle("Conoce la evolución de nuestra institución a través del tiempo")
                .isActive(true)
                .build();
    }

    private AboutAdminDTO.AboutTimelineEventDTO mapEventToDTO(AboutTimelineEvents event) {
        return AboutAdminDTO.AboutTimelineEventDTO.builder()
                .id(event.getId())
                .yearLabel(event.getYearLabel())
                .title(event.getTitle())
                .description(event.getDescription())
                .displayOrder(event.getDisplayOrder())
                .isActive(event.getIsActive())
                .build();
    }

    private AboutAdminDTO.AboutHistorySectionDTO mapSectionToDTO(AboutHistorySection section) {
        return AboutAdminDTO.AboutHistorySectionDTO.builder()
                .id(section.getId())
                .title(section.getTitle())
                .subtitle(section.getSubtitle())
                .isActive(section.getIsActive())
                .build();
    }
}
