package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.config.SiteSettings;
import com.elsalvador.coopac.projection.SiteSettingsLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la configuración global del sitio
 */
@Repository
public interface SiteSettingsRepository extends JpaRepository<SiteSettings, UUID> {

    /**
     * Encuentra la configuración más reciente del sitio ordenada por fecha de actualización
     * @return configuración del sitio más reciente o empty
     */
    Optional<SiteSettingsLite> findTopByOrderByUpdatedAtDesc();

    /**
     * Encuentra la configuración completa más reciente del sitio
     * @return configuración del sitio completa más reciente o empty
     */
    Optional<SiteSettings> findFirstByOrderByUpdatedAtDesc();
}
