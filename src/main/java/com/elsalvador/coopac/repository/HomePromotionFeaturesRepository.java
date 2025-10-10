package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomePromotionFeatures;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
