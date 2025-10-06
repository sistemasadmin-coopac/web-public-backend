package com.elsalvador.coopac.service.admin.contact;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;

import java.util.UUID;

/**
 * Servicio para actualizar canales de contacto
 */
public interface UpdateContactChannelsService {

    /**
     * Actualiza un canal de contacto
     * @param id ID del canal
     * @param updateDTO datos de actualización
     * @return canal actualizado
     */
    ContactAdminDTO.ContactChannelItemDTO updateContactChannel(UUID id, ContactAdminDTO.UpdateContactChannelDTO updateDTO);
}
