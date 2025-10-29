package com.elsalvador.coopac.repository.join;

import com.elsalvador.coopac.entity.join.JoinSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JoinSectionRepository extends JpaRepository<JoinSection, UUID> {

    /**
     * Obtiene la primera sección de join (generalmente hay solo una)
     */
    Optional<JoinSection> findFirstByOrderByCreatedAtDesc();
}

