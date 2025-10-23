package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomePromotions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las promociones del home
 */
@Repository
public interface HomePromotionsRepository extends JpaRepository<HomePromotions, UUID> {

    /**
     * Encuentra todas las promociones activas de una sección ordenadas por destacadas primero y luego por display_order
     * @param sectionId ID de la sección de promociones
     * @return lista de promociones activas ordenadas (destacadas primero)
     */
    List<HomePromotions> findBySectionIdAndIsActiveTrueOrderByIsFeaturedDescDisplayOrderAsc(UUID sectionId);

    /**
     * Encuentra todas las promociones de una sección (activas e inactivas) ordenadas por destacadas primero y luego por display_order
     * @param sectionId ID de la sección de promociones
     * @return lista de todas las promociones ordenadas (destacadas primero)
     */
    List<HomePromotions> findBySectionIdOrderByIsFeaturedDescDisplayOrderAsc(UUID sectionId);

    /**
     * Obtiene el máximo displayOrder de las promociones
     * @return el valor máximo de displayOrder o 0 si no hay registros
     */
    @Query("SELECT COALESCE(MAX(h.displayOrder), 0) FROM HomePromotions h")
    Integer findMaxDisplayOrder();
}
