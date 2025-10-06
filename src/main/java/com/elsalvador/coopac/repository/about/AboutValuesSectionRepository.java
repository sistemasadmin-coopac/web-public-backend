package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutValuesSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutValuesSectionRepository extends JpaRepository<AboutValuesSection, UUID> {

    /**
     * Obtiene la configuración activa de la sección de valores
     */
    Optional<AboutValuesSection> findFirstByIsActiveTrueOrderByUpdatedAtDesc();

    /**
     * Obtiene la configuración más reciente de la sección de valores
     */
    Optional<AboutValuesSection> findFirstByOrderByUpdatedAtDesc();

    /**
     * Método para compatibilidad con servicio público existente
     */
    Optional<AboutValuesSection> findFirstByIsActiveTrueOrderByCreatedAtAsc();
}
