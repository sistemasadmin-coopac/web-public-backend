package com.elsalvador.coopac.service.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;

public interface UpdatePageHeaderCardsService {

    /**
     * Actualiza una PageHeaderCard
     */
    PageHeaderCardsAdminDTO updatePageHeaderCards(PageHeaderCardsAdminDTO pageHeaderCardDTO);
}
