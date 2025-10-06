package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutHistorySection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutHistorySectionRepository extends JpaRepository<AboutHistorySection, UUID> {

    /**
     * Obtiene la configuración activa de la sección de historia
     */
    Optional<AboutHistorySection> findFirstByIsActiveTrueOrderByUpdatedAtDesc();

    /**
     * Obtiene la configuración más reciente de la sección de historia
     */
    Optional<AboutHistorySection> findFirstByOrderByUpdatedAtDesc();

    /**
     * Método para compatibilidad con servicio público existente
     */
    Optional<AboutHistorySection> findFirstByIsActiveTrue();
}
