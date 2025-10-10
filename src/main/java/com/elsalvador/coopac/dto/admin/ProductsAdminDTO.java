package com.elsalvador.coopac.dto.admin;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTOs para administración de productos
 */
public class ProductsAdminDTO {

    /**
     * DTO para respuesta completa de producto
     */
    public record ProductResponseDTO(
            UUID id,
            UUID categoryId,
            String slug,
            String title,
            String cardSummary,
            String detailIntro,
            String icon,
            String highlightText,
            Boolean isFeatured,
            Integer displayOrder,
            Boolean isActive,
            List<ProductFeatureDTO> features,
            List<ProductActionDTO> actions,
            List<ProductBadgeDTO> badges,
            List<ProductStepDTO> steps,
            ProductFinancialInfoDTO financialInfo
    ) {}

    /**
     * DTO para crear producto
     */
    public record CreateProductDTO(
            UUID categoryId,
            String title,
            String cardSummary,
            String detailIntro,
            String icon,
            String highlightText,
            Boolean isFeatured,
            Integer displayOrder,
            List<CreateProductFeatureDTO> features,
            List<CreateProductActionDTO> actions,
            List<CreateProductBadgeDTO> badges,
            List<CreateProductStepDTO> steps,
            CreateProductFinancialInfoDTO financialInfo
    ) {}

    /**
     * DTO para actualizar producto (SIN SLUG)
     */
    public record UpdateProductDTO(
            String title,
            String cardSummary,
            String detailIntro,
            String icon,
            String highlightText,
            Boolean isFeatured,
            Integer displayOrder,
            Boolean isActive
    ) {}

    /**
     * DTO para lista de productos (vista simplificada)
     */
    public record ProductListItemDTO(
            UUID id,
            String categoryName,
            String slug,
            String title,
            String highlightText,
            Boolean isFeatured,
            Integer displayOrder,
            Boolean isActive
    ) {}

    // DTOs para características del producto
    public record ProductFeatureDTO(
            UUID id,
            String featureText,
            Integer displayOrder
    ) {}

    public record CreateProductFeatureDTO(
            String featureText,
            Integer displayOrder
    ) {}

    public record UpdateProductFeatureDTO(
            String featureText,
            Integer displayOrder
    ) {}

    // DTOs para acciones del producto
    public record ProductActionDTO(
            UUID id,
            String label,
            String actionType,
            String actionValue,
            Boolean isPrimary,
            Integer displayOrder
    ) {}

    public record CreateProductActionDTO(
            String label,
            String actionType,
            String actionValue,
            Boolean isPrimary,
            Integer displayOrder
    ) {}

    public record UpdateProductActionDTO(
            String label,
            String actionType,
            String actionValue,
            Boolean isPrimary,
            Integer displayOrder
    ) {}

    // DTOs para badges del producto
    public record ProductBadgeDTO(
            UUID id,
            String badgeText,
            Integer displayOrder
    ) {}

    public record CreateProductBadgeDTO(
            String badgeText,
            Integer displayOrder
    ) {}

    public record UpdateProductBadgeDTO(
            String badgeText,
            Integer displayOrder
    ) {}

    // DTOs para pasos del producto
    public record ProductStepDTO(
            UUID id,
            String title,
            String description,
            String icon,
            String estimatedTime,
            Integer displayOrder,
            Boolean isActive
    ) {}

    public record CreateProductStepDTO(
            String title,
            String description,
            String icon,
            String estimatedTime,
            Integer displayOrder
    ) {}

    public record UpdateProductStepDTO(
            String title,
            String description,
            String icon,
            String estimatedTime,
            Integer displayOrder,
            Boolean isActive
    ) {}

    // DTOs para información financiera
    public record ProductFinancialInfoDTO(
            String interestRateText,
            BigDecimal interestRateValue,
            String termText,
            Integer termMonths,
            String minAmountText,
            BigDecimal minAmount,
            String currency,
            String notes
    ) {}

    public record CreateProductFinancialInfoDTO(
            String interestRateText,
            BigDecimal interestRateValue,
            String termText,
            Integer termMonths,
            String minAmountText,
            BigDecimal minAmount,
            String currency,
            String notes
    ) {}

    public record UpdateProductFinancialInfoDTO(
            String interestRateText,
            BigDecimal interestRateValue,
            String termText,
            Integer termMonths,
            String minAmountText,
            BigDecimal minAmount,
            String currency,
            String notes
    ) {}
}
