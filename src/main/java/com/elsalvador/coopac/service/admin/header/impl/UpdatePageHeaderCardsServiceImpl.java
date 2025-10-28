package com.elsalvador.coopac.service.admin.header.impl;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.repository.PageHeaderCardsRepository;
import com.elsalvador.coopac.service.admin.header.UpdatePageHeaderCardsService;
import com.elsalvador.coopac.service.admin.header.mapper.PageHeaderCardsAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdatePageHeaderCardsServiceImpl implements UpdatePageHeaderCardsService {

    private final PageHeaderCardsRepository pageHeaderCardsRepository;
    private final PageHeaderCardsAdminMapper pageHeaderCardsAdminMapper;

    @Override
    @CacheEvict(value = {HOME_PAGE_CACHE, ABOUT_PAGE_CACHE, CONTACT_PAGE_CACHE, FINANCIAL_PAGE_CACHE, PRODUCT_PAGE_CACHE, JOIN_PAGE_CACHE}, allEntries = true)
    public PageHeaderCardsAdminDTO updatePageHeaderCards(PageHeaderCardsAdminDTO pageHeaderCardDTO) {
        log.info("Actualizando PageHeaderCard con ID: {}", pageHeaderCardDTO.id());


        var existingEntity = pageHeaderCardsRepository.findById(pageHeaderCardDTO.id())
                .orElseThrow(() -> new IllegalArgumentException("PageHeaderCard no encontrada con ID: " + pageHeaderCardDTO.id()));

        existingEntity.setIcon(pageHeaderCardDTO.icon());
        existingEntity.setTitle(pageHeaderCardDTO.title());
        existingEntity.setDescription(pageHeaderCardDTO.description());

        var savedEntity = pageHeaderCardsRepository.save(existingEntity);

        return pageHeaderCardsAdminMapper.toDTO(savedEntity);
    }
}
