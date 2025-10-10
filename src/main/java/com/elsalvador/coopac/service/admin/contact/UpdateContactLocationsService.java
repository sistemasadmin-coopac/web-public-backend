package com.elsalvador.coopac.service.admin.contact;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;

import java.util.UUID;

/**
 * Servicio para actualizar ubicaciones de contacto
 */
public interface UpdateContactLocationsService {

    /**
     * Actualiza una ubicación de contacto
     * @param id ID de la ubicación
     * @param updateDTO datos de actualización
     * @return ubicación actualizada
     */
    ContactAdminDTO.ContactLocationPlaceDTO updateContactLocation(UUID id, ContactAdminDTO.UpdateContactLocationDTO updateDTO);
}
