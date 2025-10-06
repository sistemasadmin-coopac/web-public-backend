package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductBadges;
import com.elsalvador.coopac.projection.product.ProductPageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductBadgesRepository extends JpaRepository<ProductBadges, UUID> {

    /**
     * Obtiene los badges de un producto ordenados por display_order (para vista pública)
     */
    List<ProductPageView.ProductBadgeView> findByProductIdOrderByDisplayOrderAsc(UUID productId);

    /**
     * Obtiene las entidades badges de un producto ordenadas por display_order (para administración)
     */
    @Query("SELECT pb FROM ProductBadges pb WHERE pb.product.id = :productId ORDER BY pb.displayOrder ASC")
    List<ProductBadges> findEntitiesByProductIdOrderByDisplayOrderAsc(@Param("productId") UUID productId);

    /**
     * Elimina todos los badges de un producto
     */
    void deleteByProductId(UUID productId);
}
