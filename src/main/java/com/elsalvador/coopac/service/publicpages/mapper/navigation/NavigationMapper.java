package com.elsalvador.coopac.service.publicpages.mapper.navigation;

import com.elsalvador.coopac.dto.publicpage.navigation.NavigationDTO;
import com.elsalvador.coopac.entity.navigation.NavigationItems;
import com.elsalvador.coopac.entity.navigation.NavigationMenus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de Navigation a DTOs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NavigationMapper {

    /**
     * Mapea un menú de navegación completo con sus items
     */
    public NavigationDTO mapNavigation(NavigationMenus menu, String brandUrl, String logoUrl, String logoAlt,
                                     String whatsappUrl, String phone) {
        var menuDto = new NavigationDTO.MenuDTO(
                menu.getSlug(),
                menu.getName()
        );

        var brandDto = new NavigationDTO.BrandDTO(
                brandUrl,
                logoUrl,
                logoAlt
        );

        var itemDtos = menu.getItems() != null ?
                menu.getItems().stream()
                    .filter(NavigationItems::getIsActive)
                    .map(this::mapNavigationItem)
                    .collect(Collectors.toList()) :
                List.<NavigationDTO.NavigationItemDTO>of();

        var shortcutsDto = new NavigationDTO.ShortcutsDTO(
                whatsappUrl,
                phone
        );

        return new NavigationDTO(menuDto, brandDto, itemDtos, shortcutsDto);
    }

    /**
     * Mapea un item de navegación individual
     */
    private NavigationDTO.NavigationItemDTO mapNavigationItem(NavigationItems item) {
        return new NavigationDTO.NavigationItemDTO(
                item.getLabel(),
                item.getUrl(),
                item.getExternal(),
                item.getDisplayOrder()
        );
    }
}
