package com.elsalvador.coopac.service.admin.product;

import java.util.UUID;

/**
 * Servicio para eliminar productos
 */
public interface DeleteProductService {

    /**
     * Elimina un producto por su ID
     * @param productId ID del producto a eliminar
     */
    void deleteProduct(UUID productId);
}
