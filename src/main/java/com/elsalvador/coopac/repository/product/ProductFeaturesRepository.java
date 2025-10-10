package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductFeatures;
import com.elsalvador.coopac.projection.product.ProductDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductFeaturesRepository extends JpaRepository<ProductFeatures, UUID> {

    /**
     * Obtiene las características de un producto ordenadas por display_order (para vista pública)
     */
    List<ProductDetailView.ProductFeatureView> findByProductIdOrderByDisplayOrderAsc(UUID productId);

    /**
     * Obtiene las entidades características de un producto ordenadas por display_order (para administración)
     */
    @Query("SELECT pf FROM ProductFeatures pf WHERE pf.product.id = :productId ORDER BY pf.displayOrder ASC")
    List<ProductFeatures> findEntitiesByProductIdOrderByDisplayOrderAsc(@Param("productId") UUID productId);

    /**
     * Elimina todas las características de un producto
     */
    void deleteByProductId(UUID productId);
}
