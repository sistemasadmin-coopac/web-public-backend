package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la sección de promociones del home
 */
@Repository
public interface HomePromotionsSectionRepository extends JpaRepository<HomePromotionsSection, UUID> {

    /**
     * Encuentra la primera sección de promociones activa
     * @return sección de promociones activa o empty
     */
    Optional<HomePromotionsSection> findFirstByIsActiveTrue();
}
