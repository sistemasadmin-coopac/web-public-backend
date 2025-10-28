package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.ProductFinancialInfo;
import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.BusinessValidationException;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductFinancialInfoRepository;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.admin.product.ManageProductFinancialInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar información financiera de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageProductFinancialInfoServiceImpl implements ManageProductFinancialInfoService {

    private final ProductFinancialInfoRepository financialInfoRepository;
    private final ProductsRepository productsRepository;

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE, JOIN_PAGE_CACHE}, allEntries = true)
    public ProductsAdminDTO.ProductFinancialInfoDTO createFinancialInfo(UUID productId, ProductsAdminDTO.CreateProductFinancialInfoDTO createDTO) {
        log.info("Creando información financiera para producto: {}", productId);

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        // Verificar que no exista ya información financiera para este producto
        if (financialInfoRepository.existsByProductId(productId)) {
            throw new BusinessValidationException("Ya existe información financiera para el producto con ID: " + productId);
        }

        ProductFinancialInfo financialInfo = ProductFinancialInfo.builder()
                .product(product)
                .interestRateText(createDTO.interestRateText())
                .termText(createDTO.termText())
                .currency(createDTO.currency())
                .notes(createDTO.notes())
                .build();

        ProductFinancialInfo savedFinancialInfo = financialInfoRepository.save(financialInfo);
        log.info("Información financiera creada para producto: {}", productId);

        return mapToDTO(savedFinancialInfo);
    }

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE, JOIN_PAGE_CACHE}, allEntries = true)
    public ProductsAdminDTO.ProductFinancialInfoDTO updateFinancialInfo(UUID productId, ProductsAdminDTO.UpdateProductFinancialInfoDTO updateDTO) {
        log.info("Actualizando información financiera del producto: {}", productId);

        ProductFinancialInfo financialInfo = financialInfoRepository.findEntityByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Información financiera no encontrada para producto con ID: " + productId));

        if (updateDTO.interestRateText() != null) {
            financialInfo.setInterestRateText(updateDTO.interestRateText());
        }
        if (updateDTO.termText() != null) {
            financialInfo.setTermText(updateDTO.termText());
        }
        if (updateDTO.currency() != null) {
            financialInfo.setCurrency(updateDTO.currency());
        }
        if (updateDTO.notes() != null) {
            financialInfo.setNotes(updateDTO.notes());
        }

        ProductFinancialInfo updatedFinancialInfo = financialInfoRepository.save(financialInfo);
        log.info("Información financiera actualizada exitosamente");

        return mapToDTO(updatedFinancialInfo);
    }

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE, JOIN_PAGE_CACHE}, allEntries = true)
    public void deleteFinancialInfo(UUID productId) {
        log.info("Eliminando información financiera del producto: {}", productId);

        ProductFinancialInfo financialInfo = financialInfoRepository.findEntityByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Información financiera no encontrada para producto con ID: " + productId));

        financialInfoRepository.delete(financialInfo);
        log.info("Información financiera eliminada exitosamente");
    }

    /**
     * Mapea entidad a DTO
     */
    private ProductsAdminDTO.ProductFinancialInfoDTO mapToDTO(ProductFinancialInfo financialInfo) {
        return new ProductsAdminDTO.ProductFinancialInfoDTO(
                financialInfo.getInterestRateText(),
                financialInfo.getTermText(),
                financialInfo.getCurrency(),
                financialInfo.getNotes()
        );
    }
}
