package com.elsalvador.coopac.service.publicpages.mapper.product;

import com.elsalvador.coopac.dto.publicpage.product.ProductDetailDTO;
import com.elsalvador.coopac.projection.product.ProductDetailView;
import com.elsalvador.coopac.repository.product.ProductFeaturesRepository;
import com.elsalvador.coopac.repository.product.ProductFinancialInfoRepository;
import com.elsalvador.coopac.repository.product.ProductStepsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDetailMapper {

    private final ProductFeaturesRepository productFeaturesRepository;
    private final ProductStepsRepository productStepsRepository;

    private final ProductFinancialInfoRepository productFinancialInfoRepository;

    /**
     * Mapea un producto completo a DTO de detalle
     */
    public ProductDetailDTO toProductDetailDTO(ProductDetailView.ProductFullView product) {
        var header = createHeader(product);
        var sections = createSections(product.getId());

        return new ProductDetailDTO(header, sections);
    }

    /**
     * Crea el header del producto
     */
    private ProductDetailDTO.HeaderDTO createHeader(ProductDetailView.ProductFullView product) {
        var backLink = new ProductDetailDTO.BackLinkDTO("Volver a Productos", "/productos");
        var category = new ProductDetailDTO.CategorySummaryDTO(
                product.getCategory().getSlug(),
                product.getCategory().getName()
        );

        return new ProductDetailDTO.HeaderDTO(
                backLink,
                category,
                product.getTitle(),
                product.getDetailIntro() != null ? product.getDetailIntro() : product.getCardSummary(),
                product.getHighlightText(),
                product.getIcon()
        );
    }

    /**
     * Crea las secciones del producto
     */
    private ProductDetailDTO.SectionsDTO createSections(UUID productId) {
        var financialInfo = createFinancialInfo(productId);
        var features = createFeatures(productId);
        var steps = createSteps(productId);
        var ctaPanel = createCtaPanel();

        return new ProductDetailDTO.SectionsDTO(
                financialInfo,
                features,
                steps,
                ctaPanel
        );
    }

    /**
     * Crea la información financiera del producto
     */
    private ProductDetailDTO.FinancialInfoDTO createFinancialInfo(UUID productId) {
        var financialInfoOpt = productFinancialInfoRepository.findByProductId(productId);

        if (financialInfoOpt.isEmpty()) {
            log.warn("No se encontró información financiera para el producto ID: {}", productId);
            return null;
        }

        var financial = financialInfoOpt.get();
        var metrics = List.of(
                new ProductDetailDTO.MetricDTO("Tasa de Interés", financial.getInterestRateText()),
                new ProductDetailDTO.MetricDTO("Plazo", financial.getTermText())
        );

        var rawData = new ProductDetailDTO.RawFinancialDataDTO(
                financial.getInterestRateText(),
                financial.getTermText(),
                financial.getCurrency(),
                financial.getNotes()
        );

        return new ProductDetailDTO.FinancialInfoDTO(metrics, rawData);
    }

    /**
     * Crea las características del producto
     */
    private ProductDetailDTO.FeaturesDTO createFeatures(UUID productId) {
        var features = productFeaturesRepository.findByProductIdOrderByDisplayOrderAsc(productId)
                .stream()
                .map(ProductDetailView.ProductFeatureView::getFeatureText)
                .collect(Collectors.toList());

        return new ProductDetailDTO.FeaturesDTO(features);
    }

    /**
     * Crea los pasos del producto
     */
    private ProductDetailDTO.StepsDTO createSteps(UUID productId) {
        var stepItems = productStepsRepository.findByProductIdAndIsActiveTrueOrderByDisplayOrderAsc(productId)
                .stream()
                .map(this::mapStep)
                .collect(Collectors.toList());

        return new ProductDetailDTO.StepsDTO(
                "Cómo obtenerlo",
                "Sigue estos pasos y completa tu solicitud",
                stepItems
        );
    }

    /**
     * Mapea un paso individual
     */
    private ProductDetailDTO.StepItemDTO mapStep(ProductDetailView.ProductStepView step) {
        return new ProductDetailDTO.StepItemDTO(
                step.getTitle(),
                step.getDescription(),
                step.getIcon(),
                step.getEstimatedTime(),
                step.getDisplayOrder()
        );
    }

    /**
     * Crea el panel de CTA
     */
    private ProductDetailDTO.CtaPanelDTO createCtaPanel() {
        return new ProductDetailDTO.CtaPanelDTO(
                "¿Interesado en este producto?",
                "Contáctanos para más información y asesoría personalizada"
        );
    }
}
