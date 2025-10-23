package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomeStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para los elementos de estadísticas del home
 */
@Repository
public interface HomeStatsRepository extends JpaRepository<HomeStats, UUID> {

    /**
     * Encuentra todas las estadísticas activas ordenadas por display_order
     * @return lista de estadísticas activas ordenadas
     */
    List<HomeStats> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene el máximo displayOrder de las estadísticas
     * @return el valor máximo de displayOrder o 0 si no hay registros
     */
    @Query("SELECT COALESCE(MAX(h.displayOrder), 0) FROM HomeStats h")
    Integer findMaxDisplayOrder();
}
