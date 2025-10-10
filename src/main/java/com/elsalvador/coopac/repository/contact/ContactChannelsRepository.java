package com.elsalvador.coopac.repository.contact;

import com.elsalvador.coopac.entity.contact.ContactChannels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para los canales de contacto
 */
@Repository
public interface ContactChannelsRepository extends JpaRepository<ContactChannels, UUID> {

    /**
     * Obtiene todos los canales activos ordenados por display_order
     */
    List<ContactChannels> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todos los canales (activos e inactivos) ordenados por display_order
     */
    List<ContactChannels> findAllByOrderByDisplayOrderAsc();
}
