package com.elsalvador.coopac.repository.contact;

import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las entradas de horarios de contacto
 */
@Repository
public interface ContactScheduleEntriesRepository extends JpaRepository<ContactScheduleEntries, UUID> {

    /**
     * Obtiene todas las entradas de horario activas ordenadas por display_order
     */
    List<ContactScheduleEntries> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todas las entradas de horario (activas e inactivas) ordenadas por display_order
     */
    List<ContactScheduleEntries> findAllByOrderByDisplayOrderAsc();
}
