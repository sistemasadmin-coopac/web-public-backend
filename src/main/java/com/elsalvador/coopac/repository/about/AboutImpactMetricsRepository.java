package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutImpactMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AboutImpactMetricsRepository extends JpaRepository<AboutImpactMetrics, UUID> {

    /**
     * Obtiene todas las métricas de impacto activas ordenadas por display_order
     */
    List<AboutImpactMetrics> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todas las métricas ordenadas por display_order para administración
     */
    List<AboutImpactMetrics> findAllByOrderByDisplayOrderAsc();

    /**
     * Cuenta el número de métricas activas
     */
    long countByIsActiveTrue();
}
