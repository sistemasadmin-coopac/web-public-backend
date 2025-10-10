package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeCtaBlocksAdminDTO;

import java.util.List;

/**
 * Servicio para obtener bloques CTA del home
 */
public interface GetHomeCtaBlocksService {

    /**
     * Obtiene todos los bloques CTA activos ordenados
     * @return lista de bloques CTA
     */
    List<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO> getAllActiveCtaBlocks();
}
