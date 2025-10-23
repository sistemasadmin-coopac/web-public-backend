package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomePromotionFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las características de promociones del home
 */
@Repository
public interface HomePromotionFeaturesRepository extends JpaRepository<HomePromotionFeatures, UUID> {

    /**
     * Encuentra todas las características de una promoción ordenadas por display_order
     * @param promotionId ID de la promoción
     * @return lista de características ordenadas
     */
    List<HomePromotionFeatures> findByPromotionIdOrderByDisplayOrderAsc(UUID promotionId);

    /**
     * Obtiene el máximo displayOrder de las características
     * @return el valor máximo de displayOrder o 0 si no hay registros
     */
    @Query("SELECT COALESCE(MAX(h.displayOrder), 0) FROM HomePromotionFeatures h")
    Integer findMaxDisplayOrder();
}
