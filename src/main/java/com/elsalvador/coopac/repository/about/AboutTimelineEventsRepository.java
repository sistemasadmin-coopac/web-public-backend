package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutTimelineEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AboutTimelineEventsRepository extends JpaRepository<AboutTimelineEvents, UUID> {

    /**
     * Obtiene todos los eventos de timeline activos ordenados por año y display_order
     * Corregido: usa yearLabel en lugar de eventYear
     */
    @Query("SELECT t FROM AboutTimelineEvents t WHERE t.isActive = true ORDER BY t.yearLabel ASC, t.displayOrder ASC")
    List<AboutTimelineEvents> findByIsActiveTrueOrderByYearLabelAscDisplayOrderAsc();

    /**
     * Obtiene todos los eventos ordenados por año para administración
     */
    @Query("SELECT t FROM AboutTimelineEvents t ORDER BY t.yearLabel ASC, t.displayOrder ASC")
    List<AboutTimelineEvents> findAllByOrderByYearLabelAscDisplayOrderAsc();

    /**
     * Cuenta el número de eventos activos
     */
    long countByIsActiveTrue();

    /**
     * Encuentra un evento por ID (útil para validar existencia antes de actualizar)
     */
    Optional<AboutTimelineEvents> findById(UUID id);

    /**
     * Encuentra eventos por año
     */
    List<AboutTimelineEvents> findByYearLabelOrderByDisplayOrderAsc(String yearLabel);

    /**
     * Obtiene el máximo display_order actual (útil para agregar nuevos eventos al final)
     */
    @Query("SELECT COALESCE(MAX(t.displayOrder), 0) FROM AboutTimelineEvents t")
    Integer findMaxDisplayOrder();

    /**
     * Elimina un evento por ID
     * Nota: JpaRepository ya provee deleteById(UUID id), pero lo documentamos explícitamente
     */
    void deleteById(UUID id);

    /**
     * Verifica si existe un evento con el ID dado
     */
    boolean existsById(UUID id);
}
