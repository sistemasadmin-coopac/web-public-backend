package com.elsalvador.coopac.dto.publicpage.product;

import java.util.List;

public record ProductPageDTO(
    HeaderDTO header,
    SectionsDTO sections
) {

    public record HeaderDTO(
        String badgeText,
        String titleMain,
        String titleHighlight,
        String subtitle,
        ActionsDTO actions
    ) {}

    public record ActionsDTO(
        ActionLinkDTO primary
    ) {}

    public record ActionLinkDTO(
        String text,
        String url
    ) {}

    public record SectionsDTO(
        FiltersDTO filters,
        GridDTO grid
    ) {}

    public record FiltersDTO(
        String searchPlaceholder,
        List<CategoryDTO> categories
    ) {}

    public record CategoryDTO(
        String slug,
        String name,
        String icon,
        Integer order
    ) {}

    public record GridDTO(
        List<ProductItemDTO> items,
        PaginationDTO pagination
    ) {}

    public record ProductItemDTO(
        String slug,
        String title,
        String summary,
        String icon,
        CategorySummaryDTO category,
        String highlightText,
        List<String> tags,
        FinanceBriefDTO financeBrief,
        String detailsUrl,
        Integer order
    ) {}

    public record CategorySummaryDTO(
        String slug,
        String name
    ) {}

    public record FinanceBriefDTO(
        String interestRateText,
        String termText,
        String currency,
        String notes
    ) {}

    public record PaginationDTO(
        Integer page,
        Integer pageSize,
        Integer total
    ) {}
}
