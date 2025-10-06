package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.product.ProductDetailDTO;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.publicpages.GetDataProductDetailPageService;
import com.elsalvador.coopac.service.publicpages.mapper.product.ProductDetailMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataProductDetailPageServiceImpl implements GetDataProductDetailPageService {

    private final ProductsRepository productsRepository;
    private final ProductDetailMapper productDetailMapper;

    /**
     * Obtiene el detalle de un producto por su slug
     */
    public ProductDetailDTO getProductDetail(String slug) {
        log.info("Obteniendo detalle del producto con slug: {}", slug);

        var productOpt = productsRepository.findBySlugForDetail(slug);

        if (productOpt.isEmpty()) {
            log.warn("Producto no encontrado con slug: {}", slug);
            throw new ResourceNotFoundException("Producto", "slug", slug);
        }

        var product = productOpt.get();
        var productDetail = productDetailMapper.toProductDetailDTO(product);

        log.info("Detalle del producto '{}' obtenido exitosamente", product.getTitle());
        return productDetail;
    }

    /**
     * Verifica si existe un producto con el slug dado
     */
    public boolean existsBySlug(String slug) {
        return productsRepository.existsBySlugAndIsActiveTrue(slug);
    }
}
