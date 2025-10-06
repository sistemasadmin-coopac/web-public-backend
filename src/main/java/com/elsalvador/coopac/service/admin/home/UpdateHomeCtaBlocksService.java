package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeCtaBlocksAdminDTO;

import java.util.UUID;

/**
 * Servicio para actualizar bloques CTA del home
 */
public interface UpdateHomeCtaBlocksService {

    /**
     * Actualiza un bloque CTA del home
     * @param id ID del bloque CTA a actualizar
     * @param updateDTO datos de actualización
     * @return bloque CTA actualizado
     */
    HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO updateCtaBlock(UUID id, HomeCtaBlocksAdminDTO.UpdateHomeCtaBlockDTO updateDTO);
}
