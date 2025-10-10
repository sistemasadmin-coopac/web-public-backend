package com.elsalvador.coopac.service.publicpages.mapper;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.entity.product.ProductActions;
import com.elsalvador.coopac.projection.ProductCardView;
import com.elsalvador.coopac.projection.product.ProductPageView;
import com.elsalvador.coopac.repository.product.ProductActionsRepository;
import com.elsalvador.coopac.repository.product.ProductBadgesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de productos a DTOs
 */
@Component
@RequiredArgsConstructor
public class ProductsMapper {

    private final ProductBadgesRepository productBadgesRepository;
    private final ProductActionsRepository productActionsRepository;

    /**
     * Mapea productos destacados a ProductsSectionDTO con títulos dinámicos
     */
    public HomePageDTO.ProductsSectionDTO toProductsSectionDTO(List<ProductCardView> featuredProducts) {
        if (featuredProducts.isEmpty()) {
            return null;
        }

        // Generar títulos dinámicos basados en los productos destacados
        var uniqueCategories = featuredProducts.stream()
            .map(product -> product.getCategory().getName())
            .distinct()
            .collect(Collectors.toList());

        var titleHighlight = uniqueCategories.size() == 1
            ? uniqueCategories.getFirst()
            : "Soluciones Financieras";

        var subtitle = uniqueCategories.size() > 1
            ? "Descubre nuestros productos en " + String.join(", ", uniqueCategories)
            : "Conoce todos nuestros productos de " + titleHighlight.toLowerCase();

        return new HomePageDTO.ProductsSectionDTO(
            "Nuestros Productos",
            titleHighlight,
            subtitle,
            mapProducts(featuredProducts)
        );
    }

    /**
     * Mapea los productos destacados con sus badges y acciones
     */
    private List<HomePageDTO.ProductDTO> mapProducts(List<ProductCardView> products) {
        return products.stream()
            .map(product -> {
                // Obtener badges del producto
                var badges = productBadgesRepository
                    .findByProductIdOrderByDisplayOrderAsc(product.getId())
                    .stream()
                    .map(ProductPageView.ProductBadgeView::getBadgeText)
                    .collect(Collectors.toList());

                // Obtener acción primaria
                var primaryAction = productActionsRepository
                    .findFirstByProductIdAndIsPrimaryTrueOrderByDisplayOrderAsc(product.getId())
                    .map(this::mapProductAction)
                    .orElse(null);

                // Mapear categoría
                var category = new HomePageDTO.CategoryDTO(
                    product.getCategory().getSlug(),
                    product.getCategory().getName()
                );

                return new HomePageDTO.ProductDTO(
                    product.getSlug(),
                    product.getTitle(),
                    product.getCardSummary(),
                    product.getIcon(),
                    product.getHighlightText(),
                    category,
                    badges,
                    primaryAction,
                    product.getDisplayOrder()
                );
            })
            .collect(Collectors.toList());
    }

    /**
     * Mapea una acción de producto
     */
    private HomePageDTO.PrimaryActionDTO mapProductAction(ProductActions action) {
        return new HomePageDTO.PrimaryActionDTO(
            action.getLabel(),
            action.getActionType(),
            action.getActionValue()
        );
    }
}
