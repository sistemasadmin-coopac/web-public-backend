package com.elsalvador.coopac.service.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar secciones de Join
 */
public interface ManageJoinSectionService {

    /**
     * Obtiene una sección por ID
     */
    JoinAdminDTO.JoinSectionDTO getSectionById(UUID id);

    /**
     * Actualiza una sección existente
     */
    JoinAdminDTO.JoinSectionDTO updateSection(UUID id, JoinAdminDTO.JoinSectionDTO dto);
}

