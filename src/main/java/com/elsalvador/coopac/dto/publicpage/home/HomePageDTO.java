package com.elsalvador.coopac.dto.publicpage.home;
import java.util.List;
public record HomePageDTO(
    HeaderDTO header,
    SectionsDTO sections
) {
    public record HeaderDTO(
        String badgeText,
        String titleMain,
        String titleHighlight,
        String subtitle,
        ActionsDTO actions,
        List<CardDTO> cards
    ) {}
    public record ActionsDTO(
        CtaLinkDTO primary,
        CtaLinkDTO secondary
    ) {}
    public record CardDTO(
        String icon,
        String title,
        String description,
        Integer order
    ) {}
    public record SectionsDTO(
        PromotionsSectionDTO promotions,
        ProductsSectionDTO products,
        StatsSectionDTO stats,
        CtasSectionDTO ctas
    ) {}
    public record PromotionsSectionDTO(
        String titleMain,
        String titleHighlight,
        String subtitle,
        List<PromotionDTO> items
    ) {}
    public record PromotionDTO(
        String title,
        String tag,
        String description,
        String highlightText,
        List<String> features,
        Integer order,
        String imageBase64
    ) {}
    public record ProductsSectionDTO(
        String titleMain,
        String titleHighlight,
        String subtitle,
        List<ProductDTO> items
    ) {}
    public record ProductDTO(
        String slug,
        String title,
        String summary,
        String icon,
        String highlightText,
        CategoryDTO category,
        List<String> badges,
        PrimaryActionDTO primaryAction,
        Integer order
    ) {}
    public record CategoryDTO(
        String slug,
        String name
    ) {}
    public record PrimaryActionDTO(
        String label,
        String type,
        String value
    ) {}
    public record StatsSectionDTO(
        String title,
        String subtitle,
        List<StatDTO> items
    ) {}
    public record StatDTO(
        String label,
        String value,
        String icon,
        Integer order
    ) {}
    public record CtasSectionDTO(
        List<CtaDTO> mid,
        List<CtaDTO> _final
    ) {}
    public record CtaDTO(
        String title,
        String subtitle,
        CtaLinkDTO button,
        Integer order
    ) {}
    public record CtaLinkDTO(
        String text,
        String url
    ) {}
}