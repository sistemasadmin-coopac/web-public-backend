package com.elsalvador.coopac.service.admin.header.mapper;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.entity.page.PageHeaders;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre PageHeaders y PageHeaderAdminDTO
 */
@Component
public class PageHeaderAdminMapper {

    /**
     * Convierte entidad a DTO
     */
    public PageHeaderAdminDTO toDTO(PageHeaders entity) {
        if (entity == null) return null;

        return new PageHeaderAdminDTO(
            entity.getId(),
            entity.getBadgeText(),
            entity.getTitleMain(),
            entity.getTitleHighlight(),
            entity.getSubtitle(),
            entity.getDescription(),
            entity.getPrimaryCtaText(),
            entity.getPrimaryCtaUrl(),
            entity.getSecondaryCtaText(),
            entity.getSecondaryCtaUrl()
        );
    }

    /**
     * Convierte DTO a entidad (para actualizaciones)
     */
    public PageHeaders toEntity(PageHeaderAdminDTO dto) {
        if (dto == null) return null;

        return PageHeaders.builder()
            .id(dto.id())
            .badgeText(dto.badgeText())
            .titleMain(dto.titleMain())
            .titleHighlight(dto.titleHighlight())
            .subtitle(dto.subtitle())
            .description(dto.description())
            .primaryCtaText(dto.primaryCtaText())
            .primaryCtaUrl(dto.primaryCtaUrl())
            .secondaryCtaText(dto.secondaryCtaText())
            .secondaryCtaUrl(dto.secondaryCtaUrl())
            .build();
    }
}
