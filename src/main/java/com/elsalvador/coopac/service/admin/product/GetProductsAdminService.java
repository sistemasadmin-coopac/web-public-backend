package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para obtener productos en administración
 */
public interface GetProductsAdminService {

    /**
     * Obtiene todos los productos activos
     * @return lista de productos activos
     */
    List<ProductsAdminDTO.ProductListItemDTO> getAllActiveProducts();

    /**
     * Obtiene todos los productos (activos e inactivos)
     * @return lista de todos los productos
     */
    List<ProductsAdminDTO.ProductListItemDTO> getAllProducts();

    /**
     * Obtiene productos por categoría
     * @param categoryId ID de la categoría
     * @return lista de productos de la categoría
     */
    List<ProductsAdminDTO.ProductListItemDTO> getProductsByCategory(UUID categoryId);

    /**
     * Obtiene un producto completo por ID
     * @param id ID del producto
     * @return producto completo con todas sus relaciones
     */
    ProductsAdminDTO.ProductResponseDTO getProductById(UUID id);

    /**
     * Obtiene un producto por slug
     * @param slug slug del producto
     * @return producto completo
     */
    ProductsAdminDTO.ProductResponseDTO getProductBySlug(String slug);

    /**
     * Obtiene todos los productos completos con todas sus relaciones (OPTIMIZADO)
     * @return lista de productos completos
     */
    List<ProductsAdminDTO.ProductResponseDTO> getAllProductsComplete();

    /**
     * Obtiene todos los productos activos completos con todas sus relaciones (OPTIMIZADO)
     * @return lista de productos activos completos
     */
    List<ProductsAdminDTO.ProductResponseDTO> getAllActiveProductsComplete();
}
