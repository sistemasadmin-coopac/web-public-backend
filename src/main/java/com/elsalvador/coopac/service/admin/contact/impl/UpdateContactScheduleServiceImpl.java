package com.elsalvador.coopac.service.admin.contact.impl;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.contact.ContactScheduleEntriesRepository;
import com.elsalvador.coopac.service.admin.contact.UpdateContactScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para actualizar horarios de contacto
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateContactScheduleServiceImpl implements UpdateContactScheduleService {

    private final ContactScheduleEntriesRepository contactScheduleRepository;

    @Override
    public ContactAdminDTO.ContactScheduleItemDTO updateContactSchedule(UUID id, ContactAdminDTO.UpdateContactScheduleDTO updateDTO) {
        log.info("Actualizando horario de contacto con ID: {}", id);

        ContactScheduleEntries scheduleEntry = contactScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrada de horario no encontrada con ID: " + id));

        if (updateDTO.label() != null) {
            scheduleEntry.setLabel(updateDTO.label());
        }
        if (updateDTO.openTime() != null) {
            scheduleEntry.setOpenTime(updateDTO.openTime());
        }
        if (updateDTO.closeTime() != null) {
            scheduleEntry.setCloseTime(updateDTO.closeTime());
        }
        if (updateDTO.isClosed() != null) {
            scheduleEntry.setIsClosed(updateDTO.isClosed());
        }
        if (updateDTO.displayOrder() != null) {
            scheduleEntry.setDisplayOrder(updateDTO.displayOrder());
        }
        if (updateDTO.note() != null) {
            scheduleEntry.setNote(updateDTO.note());
        }
        if (updateDTO.isActive() != null) {
            scheduleEntry.setIsActive(updateDTO.isActive());
        }

        ContactScheduleEntries updatedSchedule = contactScheduleRepository.save(scheduleEntry);
        log.info("Horario de contacto actualizado exitosamente");

        return mapToDTO(updatedSchedule);
    }

    /**
     * Mapea entidad a DTO
     */
    private ContactAdminDTO.ContactScheduleItemDTO mapToDTO(ContactScheduleEntries entry) {
        return new ContactAdminDTO.ContactScheduleItemDTO(
                entry.getId(),
                entry.getLabel(),
                entry.getOpenTime() != null ? entry.getOpenTime().toString() : "",
                entry.getCloseTime() != null ? entry.getCloseTime().toString() : "",
                entry.getIsClosed(),
                entry.getDisplayOrder(),
                entry.getNote(),
                entry.getIsActive()
        );
    }
}
