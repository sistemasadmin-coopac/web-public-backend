package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductFinancialInfo;
import com.elsalvador.coopac.projection.product.ProductDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductFinancialInfoRepository extends JpaRepository<ProductFinancialInfo, UUID> {

    /**
     * Obtiene la información financiera de un producto (para vista pública)
     */
    Optional<ProductDetailView.ProductFinancialInfoView> findByProductId(UUID productId);

    /**
     * Obtiene la entidad información financiera de un producto (para administración)
     */
    @Query("SELECT pfi FROM ProductFinancialInfo pfi WHERE pfi.product.id = :productId")
    Optional<ProductFinancialInfo> findEntityByProductId(@Param("productId") UUID productId);

    /**
     * Verifica si existe información financiera para un producto
     */
    boolean existsByProductId(UUID productId);

    /**
     * Elimina la información financiera de un producto
     */
    void deleteByProductId(UUID productId);
}
