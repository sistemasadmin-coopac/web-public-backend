package com.elsalvador.coopac.service.publicpages.mapper;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.entity.page.PageHeaderCards;
import com.elsalvador.coopac.entity.page.PageHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de header de página a DTOs
 */
@Component
public class PageHeaderMapper {

    /**
     * Mapea un PageHeaders a HeaderDTO
     */
    public HomePageDTO.HeaderDTO toHeaderDTO(PageHeaders pageHeader, List<PageHeaderCards> headerCards) {
        if (pageHeader == null) {
            return null;
        }

        return new HomePageDTO.HeaderDTO(
            pageHeader.getBadgeText(),
            pageHeader.getTitleMain(),
            pageHeader.getTitleHighlight(),
            pageHeader.getSubtitle(),
            mapActions(pageHeader),
            mapHeaderCards(headerCards)
        );
    }

    /**
     * Mapea las acciones del header
     */
    private HomePageDTO.ActionsDTO mapActions(PageHeaders pageHeader) {
        var primary = new HomePageDTO.CtaLinkDTO(
            pageHeader.getPrimaryCtaText(),
            pageHeader.getPrimaryCtaUrl()
        );

        var secondary = new HomePageDTO.CtaLinkDTO(
            pageHeader.getSecondaryCtaText(),
            pageHeader.getSecondaryCtaUrl()
        );

        return new HomePageDTO.ActionsDTO(primary, secondary);
    }

    /**
     * Mapea las tarjetas del header
     */
    private List<HomePageDTO.CardDTO> mapHeaderCards(List<PageHeaderCards> headerCards) {
        return headerCards.stream()
            .map(card -> new HomePageDTO.CardDTO(
                card.getIcon(),
                card.getTitle(),
                card.getDescription(),
                card.getDisplayOrder()
            ))
            .collect(Collectors.toList());
    }
}
