package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutImpactSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutImpactSectionRepository extends JpaRepository<AboutImpactSection, UUID> {

    /**
     * Obtiene la configuración activa de la sección de impacto
     */
    Optional<AboutImpactSection> findFirstByIsActiveTrueOrderByUpdatedAtDesc();

    /**
     * Obtiene la configuración más reciente de la sección de impacto
     */
    Optional<AboutImpactSection> findFirstByOrderByUpdatedAtDesc();

    /**
     * Método para compatibilidad con servicio público existente
     */
    Optional<AboutImpactSection> findFirstByIsActiveTrueOrderByCreatedAtAsc();
}
