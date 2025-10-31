package com.elsalvador.coopac.service.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar beneficios especiales de Join
 */
public interface ManageJoinSpecialBenefitService {

    /**
     * Obtiene todos los beneficios especiales
     */
    JoinAdminDTO.JoinSpecialBenefitListDTO getAllSpecialBenefits();

    /**
     * Obtiene un beneficio especial por ID
     */
    JoinAdminDTO.JoinSpecialBenefitDTO getSpecialBenefitById(UUID id);

    /**
     * Obtiene todos los beneficios especiales de una sección
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getSpecialBenefitsBySection(UUID joinSectionId);

    /**
     * Crea un nuevo beneficio especial
     */
    JoinAdminDTO.JoinSpecialBenefitDTO createSpecialBenefit(JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto);

    /**
     * Actualiza un beneficio especial existente
     */
    JoinAdminDTO.JoinSpecialBenefitDTO updateSpecialBenefit(UUID id, JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto);

    /**
     * Elimina un beneficio especial
     */
    void deleteSpecialBenefit(UUID id);
}

