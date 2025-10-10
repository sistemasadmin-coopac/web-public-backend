package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomeStatsSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la sección de estadísticas del home
 */
@Repository
public interface HomeStatsSectionRepository extends JpaRepository<HomeStatsSection, UUID> {

    /**
     * Encuentra la primera sección de estadísticas activa
     * @return sección de estadísticas activa o empty
     */
    Optional<HomeStatsSection> findFirstByIsActiveTrue();
}
