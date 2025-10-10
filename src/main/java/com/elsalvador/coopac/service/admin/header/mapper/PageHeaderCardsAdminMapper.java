package com.elsalvador.coopac.service.admin.header.mapper;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.entity.page.PageHeaderCards;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper para convertir entre PageHeaderCards y PageHeaderCardsAdminDTO
 */
@Component
public class PageHeaderCardsAdminMapper {

    /**
     * Convierte entidad a DTO
     */
    public PageHeaderCardsAdminDTO toDTO(PageHeaderCards entity) {
        if (entity == null) return null;

        return new PageHeaderCardsAdminDTO(
            entity.getId(),
            entity.getIcon(),
            entity.getTitle(),
            entity.getDescription()
        );
    }

    /**
     * Convierte lista de entidades a lista de DTOs
     */
    public List<PageHeaderCardsAdminDTO> toDTOList(List<PageHeaderCards> entities) {
        return entities.stream()
            .map(this::toDTO)
            .toList();
    }

    /**
     * Convierte DTO a entidad (para actualizaciones)
     */
    public PageHeaderCards toEntity(PageHeaderCardsAdminDTO dto) {
        if (dto == null) return null;

        return PageHeaderCards.builder()
            .id(dto.id())
            .icon(dto.icon())
            .title(dto.title())
            .description(dto.description())
            .build();
    }
}
