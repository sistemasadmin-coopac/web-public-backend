package com.elsalvador.coopac.service.admin.contact.impl;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.exception.BusinessValidationException;
import com.elsalvador.coopac.repository.contact.ContactScheduleEntriesRepository;
import com.elsalvador.coopac.service.admin.contact.UpdateContactScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.CONTACT_PAGE_CACHE;

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
    @CacheEvict(value = CONTACT_PAGE_CACHE, allEntries = true)
    public ContactAdminDTO.ContactScheduleItemDTO updateContactSchedule(UUID id, ContactAdminDTO.UpdateContactScheduleDTO updateDTO) {
        log.info("Actualizando horario de contacto con ID: {}", id);

        ContactScheduleEntries scheduleEntry = contactScheduleRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Horario de contacto no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Entrada de horario no encontrada con ID: " + id);
                });

        // Validar que openTime sea menor que closeTime
        if (updateDTO.openTime() != null && updateDTO.closeTime() != null) {
            if (updateDTO.openTime().isAfter(updateDTO.closeTime())) {
                log.warn("Validación fallida: openTime {} es después que closeTime {}",
                        updateDTO.openTime(), updateDTO.closeTime());
                throw new BusinessValidationException(
                        "openTime",
                        updateDTO.openTime(),
                        "La hora de apertura debe ser menor que la hora de cierre"
                );
            }

            if (updateDTO.openTime().equals(updateDTO.closeTime())) {
                log.warn("Validación fallida: openTime y closeTime son iguales: {}", updateDTO.openTime());
                throw new BusinessValidationException(
                        "closeTime",
                        updateDTO.closeTime(),
                        "La hora de cierre no puede ser igual a la hora de apertura"
                );
            }
        }

        // Solo permitir actualización de openTime y closeTime
        if (updateDTO.openTime() != null) {
            scheduleEntry.setOpenTime(updateDTO.openTime());
            log.debug("Hora de apertura actualizada a: {}", updateDTO.openTime());
        }
        if (updateDTO.closeTime() != null) {
            scheduleEntry.setCloseTime(updateDTO.closeTime());
            log.debug("Hora de cierre actualizada a: {}", updateDTO.closeTime());
        }

        ContactScheduleEntries updatedSchedule = contactScheduleRepository.save(scheduleEntry);
        log.info("Horario de contacto actualizado exitosamente con ID: {}", id);

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
