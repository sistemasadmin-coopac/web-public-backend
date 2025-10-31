package com.elsalvador.coopac.service.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar grupos de requisitos de Join
 */
public interface ManageJoinRequirementGroupService {

    /**
     * Obtiene todos los grupos de requisitos
     */
    JoinAdminDTO.JoinRequirementGroupListDTO getAllGroups();

    /**
     * Obtiene un grupo de requisitos por ID
     */
    JoinAdminDTO.JoinRequirementGroupDTO getGroupById(UUID id);

    /**
     * Obtiene todos los grupos de una sección
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getGroupsBySection(UUID joinSectionId);

    /**
     * Crea un nuevo grupo de requisitos
     */
    JoinAdminDTO.JoinRequirementGroupDTO createGroup(JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto);

    /**
     * Actualiza un grupo de requisitos existente
     */
    JoinAdminDTO.JoinRequirementGroupDTO updateGroup(UUID id, JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto);

    /**
     * Elimina un grupo de requisitos
     */
    void deleteGroup(UUID id);
}

