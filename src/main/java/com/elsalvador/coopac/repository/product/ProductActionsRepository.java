package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductActions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para las acciones de productos
 */
@Repository
public interface ProductActionsRepository extends JpaRepository<ProductActions, UUID> {

    /**
     * Encuentra la primera acción primaria de un producto ordenada por display_order
     * @param productId ID del producto
     * @return acción primaria del producto o empty
     */
    Optional<ProductActions> findFirstByProductIdAndIsPrimaryTrueOrderByDisplayOrderAsc(UUID productId);

    /**
     * Encuentra todas las acciones de un producto ordenadas por display_order
     * @param productId ID del producto
     * @return lista de acciones del producto
     */
    List<ProductActions> findByProductIdOrderByDisplayOrderAsc(UUID productId);

    /**
     * Elimina todas las acciones de un producto
     */
    void deleteByProductId(UUID productId);
}
