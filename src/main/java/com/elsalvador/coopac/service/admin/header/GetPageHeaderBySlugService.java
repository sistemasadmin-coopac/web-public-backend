package com.elsalvador.coopac.service.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;

public interface GetPageHeaderBySlugService {

    /**
     * Obtiene headers por slug de página
     */
    PageHeaderAdminDTO getPageHeaderBySlug(String pageSlug);
}
