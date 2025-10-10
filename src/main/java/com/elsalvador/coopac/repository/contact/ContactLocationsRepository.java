package com.elsalvador.coopac.repository.contact;

import com.elsalvador.coopac.entity.contact.ContactLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las ubicaciones de contacto
 */
@Repository
public interface ContactLocationsRepository extends JpaRepository<ContactLocations, UUID> {

    /**
     * Obtiene todas las ubicaciones activas ordenadas por display_order
     */
    List<ContactLocations> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todas las ubicaciones (activas e inactivas) ordenadas por display_order
     */
    List<ContactLocations> findAllByOrderByDisplayOrderAsc();
}
