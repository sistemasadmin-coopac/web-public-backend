package com.elsalvador.coopac.projection.product;

import java.util.UUID;

/**
 * Proyecciones para el detalle de productos
 */
public class ProductDetailView {

    // Proyección para el producto completo con detalles
    public interface ProductFullView {
        UUID getId();
        String getSlug();
        String getTitle();
        String getCardSummary();
        String getDetailIntro();
        String getIcon();
        String getHighlightText();
        CategorySummary getCategory();

        interface CategorySummary {
            String getSlug();
            String getName();
        }
    }

    // Proyección para características del producto
    public interface ProductFeatureView {
        String getFeatureText();
        Integer getDisplayOrder();
    }

    // Proyección para pasos del producto
    public interface ProductStepView {
        String getTitle();
        String getDescription();
        String getIcon();
        String getEstimatedTime();
        Integer getDisplayOrder();
    }

    // Proyección para información financiera completa
    public interface ProductFinancialInfoView {
        String getInterestRateText();
        String getTermText();
        String getCurrency();
        String getNotes();
    }

    // Proyección para acciones del producto
    public interface ProductActionView {
        String getLabel();
        String getActionType();
        String getActionValue();
        Boolean getIsPrimary();
        Integer getDisplayOrder();
    }
}
