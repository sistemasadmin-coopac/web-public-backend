package com.elsalvador.coopac.service.admin.header.impl;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.repository.PageHeaderCardsRepository;
import com.elsalvador.coopac.service.admin.header.GetPageHeaderCardsByHeaderService;
import com.elsalvador.coopac.service.admin.header.mapper.PageHeaderCardsAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetPageHeaderCardsByHeaderServiceImpl implements GetPageHeaderCardsByHeaderService {

    private final PageHeaderCardsRepository pageHeaderCardsRepository;
    private final PageHeaderCardsAdminMapper pageHeaderCardsAdminMapper;

    @Override
    public List<PageHeaderCardsAdminDTO> getPageHeaderCardsByHeader(String headerId) {
        log.debug("Obteniendo tarjetas por header ID: {}", headerId);
        var entities = pageHeaderCardsRepository.findByHeaderIdOrderByDisplayOrderAsc(UUID.fromString(headerId));
        return pageHeaderCardsAdminMapper.toDTOList(entities);
    }
}
