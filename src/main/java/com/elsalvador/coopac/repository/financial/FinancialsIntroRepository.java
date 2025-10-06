package com.elsalvador.coopac.repository.financial;

import com.elsalvador.coopac.entity.financial.FinancialsIntro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinancialsIntroRepository extends JpaRepository<FinancialsIntro, UUID> {

    /**
     * Obtiene la introducción activa para la página financiera
     */
    Optional<FinancialsIntro> findFirstByIsActiveTrueOrderByCreatedAtDesc();
}
