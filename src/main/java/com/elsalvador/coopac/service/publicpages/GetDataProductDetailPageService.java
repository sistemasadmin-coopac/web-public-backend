package com.elsalvador.coopac.service.publicpages;

import com.elsalvador.coopac.dto.publicpage.product.ProductDetailDTO;

public interface GetDataProductDetailPageService {
    ProductDetailDTO getProductDetail(String slug);
}
