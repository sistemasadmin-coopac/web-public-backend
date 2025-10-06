package com.elsalvador.coopac.repository.about;


import com.elsalvador.coopac.entity.about.AboutTimelineEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AboutTimelineEventRepository extends JpaRepository<AboutTimelineEvents, UUID> {

    /**
     * Obtiene todos los eventos del timeline activos ordenados por orden de visualización
     */
    List<AboutTimelineEvents> findByIsActiveTrueOrderByDisplayOrderAsc();
}
