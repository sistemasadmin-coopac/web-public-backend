package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutBoardSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutBoardSectionRepository extends JpaRepository<AboutBoardSection, UUID> {

    /**
     * Obtiene la configuración activa de la sección de junta directiva
     */
    Optional<AboutBoardSection> findFirstByIsActiveTrueOrderByUpdatedAtDesc();

    /**
     * Obtiene la configuración más reciente de la sección de junta directiva
     */
    Optional<AboutBoardSection> findFirstByOrderByUpdatedAtDesc();

    /**
     * Método para compatibilidad con servicio público existente
     */
    Optional<AboutBoardSection> findFirstByIsActiveTrueOrderByCreatedAtAsc();
}
