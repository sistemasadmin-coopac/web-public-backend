package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.product.ProductDetailDTO;
import com.elsalvador.coopac.dto.publicpage.product.ProductPageDTO;
import com.elsalvador.coopac.service.publicpages.GetDataProductDetailPageService;
import com.elsalvador.coopac.service.publicpages.GetDataProductPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class GetDataProductPageController {

    private final GetDataProductPageService getDataProductPageService;
    private final GetDataProductDetailPageService getDataProductDetailPageService;

    @GetMapping("/page")
    public ProductPageDTO getProductsPage() {
        return getDataProductPageService.getProductsPageData();
    }

    @GetMapping("/{slug}")
    public ProductDetailDTO getProductDetail(@PathVariable String slug) {
        return getDataProductDetailPageService.getProductDetail(slug);
    }
}
