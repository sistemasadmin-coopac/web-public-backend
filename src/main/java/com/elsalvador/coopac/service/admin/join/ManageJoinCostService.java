package com.elsalvador.coopac.service.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar costos de Join
 */
public interface ManageJoinCostService {

    /**
     * Obtiene todos los costos
     */
    JoinAdminDTO.JoinCostListDTO getAllCosts();

    /**
     * Obtiene un costo por ID
     */
    JoinAdminDTO.JoinCostDTO getCostById(UUID id);

    /**
     * Obtiene todos los costos de una sección
     */
    JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getCostsBySection(UUID joinSectionId);

    /**
     * Crea un nuevo costo
     */
    JoinAdminDTO.JoinCostDTO createCost(JoinAdminDTO.CreateUpdateJoinCostDTO dto);

    /**
     * Actualiza un costo existente
     */
    JoinAdminDTO.JoinCostDTO updateCost(UUID id, JoinAdminDTO.CreateUpdateJoinCostDTO dto);

    /**
     * Elimina un costo
     */
    void deleteCost(UUID id);
}

