package com.elsalvador.coopac.service.admin.contact;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;

import java.util.UUID;

/**
 * Servicio para actualizar horarios de contacto
 */
public interface UpdateContactScheduleService {

    /**
     * Actualiza una entrada de horario
     * @param id ID de la entrada de horario
     * @param updateDTO datos de actualización
     * @return entrada de horario actualizada
     */
    ContactAdminDTO.ContactScheduleItemDTO updateContactSchedule(UUID id, ContactAdminDTO.UpdateContactScheduleDTO updateDTO);
}
