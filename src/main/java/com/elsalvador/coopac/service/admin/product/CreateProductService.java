package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

/**
 * Servicio para crear productos
 */
public interface CreateProductService {

    /**
     * Crea un nuevo producto con todas sus relaciones
     * @param createDTO datos del producto a crear
     * @return producto creado
     */
    ProductsAdminDTO.ProductResponseDTO createProduct(ProductsAdminDTO.CreateProductDTO createDTO);
}
