package com.elsalvador.coopac.service.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar beneficios de Join
 */
public interface ManageJoinBenefitService {

    /**
     * Obtiene todos los beneficios
     */
    JoinAdminDTO.JoinBenefitListDTO getAllBenefits();

    /**
     * Obtiene un beneficio por ID
     */
    JoinAdminDTO.JoinBenefitDTO getBenefitById(UUID id);

    /**
     * Obtiene todos los beneficios de una sección
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getBenefitsBySection(UUID joinSectionId);

    /**
     * Crea un nuevo beneficio
     */
    JoinAdminDTO.JoinBenefitDTO createBenefit(JoinAdminDTO.CreateUpdateJoinBenefitDTO dto);

    /**
     * Actualiza un beneficio existente
     */
    JoinAdminDTO.JoinBenefitDTO updateBenefit(UUID id, JoinAdminDTO.CreateUpdateJoinBenefitDTO dto);

    /**
     * Elimina un beneficio
     */
    void deleteBenefit(UUID id);
}

