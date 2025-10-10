package com.elsalvador.coopac.service.admin.header.impl;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.repository.PageHeaderCardsRepository;
import com.elsalvador.coopac.service.admin.header.UpdatePageHeaderCardsService;
import com.elsalvador.coopac.service.admin.header.mapper.PageHeaderCardsAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdatePageHeaderCardsServiceImpl implements UpdatePageHeaderCardsService {

    private final PageHeaderCardsRepository pageHeaderCardsRepository;
    private final PageHeaderCardsAdminMapper pageHeaderCardsAdminMapper;

    @Override
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
