package com.elsalvador.coopac.projection.product;

import java.util.UUID;

/**
 * Proyecciones para la página de productos
 */
public class ProductPageView {

    // Proyección para categorías
    public interface CategoryView {
        UUID getId();
        String getName();
        String getSlug();
        String getDescription();
        String getIcon();
        Integer getDisplayOrder();
    }

    // Proyección para productos en la grilla
    public interface ProductGridView {
        UUID getId();
        String getSlug();
        String getTitle();
        String getCardSummary();
        String getIcon();
        String getHighlightText();
        Integer getDisplayOrder();
        CategoryLite getCategory();

        interface CategoryLite {
            String getSlug();
            String getName();
        }
    }

    // Proyección para badges de productos
    public interface ProductBadgeView {
        String getBadgeText();
        Integer getDisplayOrder();
    }

    // Proyección para información financiera básica
    public interface ProductFinancialView {
        String getInterestRateText();
        Double getInterestRateValue();
        String getTermText();
        Integer getTermMonths();
        String getMinAmountText();
        Double getMinAmount();
        String getCurrency();
    }
}
