package com.elsalvador.coopac.service.admin.header.impl;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.service.admin.header.GetPageHeaderBySlugService;
import com.elsalvador.coopac.service.admin.header.mapper.PageHeaderAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetPageHeaderBySlugServiceImpl implements GetPageHeaderBySlugService {

    private final PageHeadersRepository pageHeadersRepository;
    private final PageHeaderAdminMapper pageHeaderAdminMapper;

    @Override
    public PageHeaderAdminDTO getPageHeaderBySlug(String pageSlug) {
        log.debug("Obteniendo headers por slug: {}", pageSlug);
        var entities = pageHeadersRepository.findByPageSlug(pageSlug);
        if (entities.isEmpty()) {
            log.warn("No se encontraro el headers para el slug: {}", pageSlug);
            return null;
        }
        return pageHeaderAdminMapper.toDTO(entities.get());
    }
}
