package com.elsalvador.coopac.service.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;

public interface UpdatePageHeaderService {

    /**
     * Actualiza un PageHeader
     */
    PageHeaderAdminDTO updatePageHeader(PageHeaderAdminDTO pageHeaderDTO);
}
