package com.elsalvador.coopac.service.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;

import java.util.List;

public interface GetPageHeaderCardsByHeaderService {

    /**
     * Obtiene tarjetas por header ID
     */
    List<PageHeaderCardsAdminDTO> getPageHeaderCardsByHeader(String headerId);
}
