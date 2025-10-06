package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.home.HomeCtaBlocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para los bloques de CTA del home
 */
@Repository
public interface HomeCtaBlocksRepository extends JpaRepository<HomeCtaBlocks, UUID> {

    /**
     * Encuentra todos los CTAs activos ordenados por posición y display_order
     * @return lista de todos los CTAs activos ordenados
     */
    List<HomeCtaBlocks> findByIsActiveTrueOrderByPositionAscDisplayOrderAsc();
}
