package com.elsalvador.coopac.dto.publicpage.product;

import java.util.List;

public record ProductDetailDTO(
    HeaderDTO header,
    SectionsDTO sections
) {

    public record HeaderDTO(
        BackLinkDTO back,
        CategorySummaryDTO category,
        String title,
        String intro,
        String highlightText,
        String icon
    ) {}

    public record BackLinkDTO(
        String label,
        String url
    ) {}

    public record CategorySummaryDTO(
        String slug,
        String name
    ) {}

    public record SectionsDTO(
        FinancialInfoDTO financialInfo,
        FeaturesDTO features,
        StepsDTO steps,
        CtaPanelDTO ctaPanel
    ) {}

    public record FinancialInfoDTO(
        List<MetricDTO> metrics,
        RawFinancialDataDTO raw
    ) {}

    public record MetricDTO(
        String label,
        String value
    ) {}

    public record RawFinancialDataDTO(
        String interestRateText,
        String termText,
        String currency,
        String notes
    ) {}

    public record FeaturesDTO(
        List<String> items
    ) {}

    public record StepsDTO(
        String title,
        String subtitle,
        List<StepItemDTO> items
    ) {}

    public record StepItemDTO(
        String title,
        String description,
        String icon,
        String estimatedTime,
        Integer order
    ) {}

    public record CtaPanelDTO(
        String title,
        String subtitle
    ) {}
}
