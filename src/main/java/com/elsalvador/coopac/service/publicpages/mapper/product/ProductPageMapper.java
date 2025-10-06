package com.elsalvador.coopac.service.publicpages.mapper.product;

import com.elsalvador.coopac.dto.publicpage.product.ProductPageDTO;
import com.elsalvador.coopac.projection.product.ProductDetailView;
import com.elsalvador.coopac.projection.product.ProductPageView;
import com.elsalvador.coopac.repository.product.ProductBadgesRepository;
import com.elsalvador.coopac.repository.product.ProductFinancialInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductPageMapper
{

    private final ProductBadgesRepository productBadgesRepository;
    private final ProductFinancialInfoRepository productFinancialInfoRepository;

    /**
     * Mapea las categorías a DTOs
     */
    public List<ProductPageDTO.CategoryDTO> mapCategories(List<ProductPageView.CategoryView> categories) {
        return categories.stream()
                .map(this::mapCategory)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una categoría individual
     */
    private ProductPageDTO.CategoryDTO mapCategory(ProductPageView.CategoryView category) {
        return new ProductPageDTO.CategoryDTO(
                category.getSlug(),
                category.getName(),
                category.getIcon(),
                category.getDisplayOrder()
        );
    }

    /**
     * Mapea los productos a DTOs para la grilla
     */
    public List<ProductPageDTO.ProductItemDTO> mapProducts(List<ProductPageView.ProductGridView> products) {
        return products.stream()
                .map(this::mapProduct)
                .collect(Collectors.toList());
    }

    /**
     * Mapea un producto individual para la grilla
     */
    private ProductPageDTO.ProductItemDTO mapProduct(ProductPageView.ProductGridView product) {
        // Obtener badges
        var badges = productBadgesRepository.findByProductIdOrderByDisplayOrderAsc(product.getId())
                .stream()
                .map(ProductPageView.ProductBadgeView::getBadgeText)
                .collect(Collectors.toList());

        // Obtener información financiera
        ProductPageDTO.FinanceBriefDTO financialInfo = productFinancialInfoRepository.findByProductId(product.getId())
                .map(this::mapFinancialBrief)
                .orElse(null);

        // Mapear categoría
        var categoryDto = new ProductPageDTO.CategorySummaryDTO(
                product.getCategory().getSlug(),
                product.getCategory().getName()
        );

        return new ProductPageDTO.ProductItemDTO(
                product.getSlug(),
                product.getTitle(),
                product.getCardSummary(),
                product.getIcon(),
                categoryDto,
                product.getHighlightText(),
                badges,
                financialInfo,
                "/productos/" + product.getSlug(),
                product.getDisplayOrder()
        );
    }

    /**
     * Mapea información financiera básica para la grilla
     */
    private ProductPageDTO.FinanceBriefDTO mapFinancialBrief(ProductDetailView.ProductFinancialInfoView financial) {
        return new ProductPageDTO.FinanceBriefDTO(
                financial.getInterestRateText(),
                financial.getInterestRateValue(),
                financial.getTermText(),
                financial.getTermMonths(),
                financial.getMinAmountText(),
                financial.getMinAmount() != null ? financial.getMinAmount().intValue() : null,
                financial.getCurrency()
        );
    }

    /**
     * Crea el DTO de paginación por defecto
     */
    public ProductPageDTO.PaginationDTO createDefaultPagination(int totalProducts) {
        return new ProductPageDTO.PaginationDTO(
                1,
                totalProducts,
                totalProducts
        );
    }
}
