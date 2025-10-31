package com.elsalvador.coopac.service.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar requisitos de beneficios especiales de Join
 */
public interface ManageJoinSpecialBenefitRequirementService {

    /**
     * Obtiene un requisito de beneficio especial por ID
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementDTO getRequirementById(UUID id);

    /**
     * Obtiene todos los requisitos
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getAllRequirements();

    /**
     * Obtiene todos los requisitos de un beneficio especial
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getRequirementsByBenefit(UUID joinSpecialBenefitId);

    /**
     * Crea un nuevo requisito para un beneficio especial
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementDTO createRequirement(JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto);

    /**
     * Actualiza un requisito de beneficio especial
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementDTO updateRequirement(UUID id, JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto);

    /**
     * Elimina un requisito de beneficio especial
     */
    void deleteRequirement(UUID id);
}

