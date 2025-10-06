package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutMissionVision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutMissionVisionRepository extends JpaRepository<AboutMissionVision, UUID> {

    /**
     * Obtiene la configuración activa de misión y visión
     */
    Optional<AboutMissionVision> findFirstByIsActiveTrueOrderByUpdatedAtDesc();

    /**
     * Obtiene la configuración más reciente de misión y visión
     */
    Optional<AboutMissionVision> findFirstByOrderByUpdatedAtDesc();

    /**
     * Método para compatibilidad con servicio público existente
     */
    Optional<AboutMissionVision> findFirstByIsActiveTrueOrderByCreatedAtAsc();
}
