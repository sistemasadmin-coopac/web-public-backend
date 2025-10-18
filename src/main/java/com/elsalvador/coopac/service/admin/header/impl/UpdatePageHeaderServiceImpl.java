package com.elsalvador.coopac.service.admin.header.impl;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.entity.page.PageHeaders;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.service.admin.header.UpdatePageHeaderService;
import com.elsalvador.coopac.service.admin.header.mapper.PageHeaderAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdatePageHeaderServiceImpl implements UpdatePageHeaderService {

    private final PageHeadersRepository pageHeadersRepository;
    private final PageHeaderAdminMapper pageHeaderAdminMapper;

    @Override
    @CacheEvict(value = {HOME_PAGE_CACHE, ABOUT_PAGE_CACHE, CONTACT_PAGE_CACHE, FINANCIAL_PAGE_CACHE, PRODUCT_PAGE_CACHE}, allEntries = true)
    public PageHeaderAdminDTO updatePageHeader(PageHeaderAdminDTO pageHeaderDTO) {
        log.info("Actualizando PageHeader con ID: {}", pageHeaderDTO.id());
        Optional<PageHeaders> pageHeaderDb = pageHeadersRepository.findById(pageHeaderDTO.id());

        if (pageHeaderDb.isEmpty()) {
            log.warn("PageHeader no encontrado con ID: {}", pageHeaderDTO.id());
            throw new RuntimeException("PageHeader not found with ID: " + pageHeaderDTO.id());
        }

        pageHeaderDb.get().setBadgeText(pageHeaderDTO.badgeText());
        pageHeaderDb.get().setTitleMain(pageHeaderDTO.titleMain());
        pageHeaderDb.get().setTitleHighlight(pageHeaderDTO.titleHighlight());
        pageHeaderDb.get().setSubtitle(pageHeaderDTO.subtitle());
        pageHeaderDb.get().setDescription(pageHeaderDTO.description());
        pageHeaderDb.get().setPrimaryCtaText(pageHeaderDTO.primaryCtaText());
        pageHeaderDb.get().setPrimaryCtaUrl(pageHeaderDTO.primaryCtaUrl());
        pageHeaderDb.get().setSecondaryCtaText(pageHeaderDTO.secondaryCtaText());
        pageHeaderDb.get().setSecondaryCtaUrl(pageHeaderDTO.secondaryCtaUrl());

        var savedEntity = pageHeadersRepository.save(pageHeaderDb.get());
        return pageHeaderAdminMapper.toDTO(savedEntity);
    }
}
