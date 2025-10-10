package com.elsalvador.coopac.projection;

import java.util.UUID;

/**
 * Proyección para las tarjetas de productos en el home
 */
public interface ProductCardView {
    UUID getId();
    String getSlug();
    String getTitle();
    String getCardSummary();
    String getIcon();
    String getHighlightText();
    Integer getDisplayOrder();
    CategoryLite getCategory();

    /**
     * Proyección anidada para la categoría
     */
    interface CategoryLite {
        String getSlug();
        String getName();
    }
}
