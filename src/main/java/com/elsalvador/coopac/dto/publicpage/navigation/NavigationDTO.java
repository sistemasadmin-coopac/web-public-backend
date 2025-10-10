package com.elsalvador.coopac.dto.publicpage.navigation;

import java.util.List;

public record NavigationDTO(
    MenuDTO menu,
    BrandDTO brand,
    List<NavigationItemDTO> items,
    ShortcutsDTO shortcuts
) {

    public record MenuDTO(
        String slug,
        String name
    ) {}

    public record BrandDTO(
        String url,
        String logoUrl,
        String logoAlt
    ) {}

    public record NavigationItemDTO(
        String label,
        String url,
        Boolean external,
        Integer order
    ) {}

    public record ShortcutsDTO(
        String whatsappUrl,
        String phone
    ) {}
}
