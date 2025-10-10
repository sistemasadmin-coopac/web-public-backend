package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductSteps;
import com.elsalvador.coopac.projection.product.ProductDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductStepsRepository extends JpaRepository<ProductSteps, UUID> {

    /**
     * Obtiene los pasos de un producto activos ordenados por display_order (para vista pública)
     */
    List<ProductDetailView.ProductStepView> findByProductIdAndIsActiveTrueOrderByDisplayOrderAsc(UUID productId);

    /**
     * Obtiene las entidades pasos de un producto activos ordenadas por display_order (para administración)
     */
    @Query("SELECT ps FROM ProductSteps ps WHERE ps.product.id = :productId AND ps.isActive = true ORDER BY ps.displayOrder ASC")
    List<ProductSteps> findEntitiesByProductIdAndIsActiveTrueOrderByDisplayOrderAsc(@Param("productId") UUID productId);

    /**
     * Elimina todos los pasos de un producto
     */
    void deleteByProductId(UUID productId);
}
