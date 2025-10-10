package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.page.PageHeaderCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio para las tarjetas de headers de páginas
 */
@Repository
public interface PageHeaderCardsRepository extends JpaRepository<PageHeaderCards, UUID> {

    /**
     * Encuentra todas las tarjetas activas de un header ordenadas por display_order
     * @param headerId ID del header
     * @return lista de tarjetas activas ordenadas
     */
    List<PageHeaderCards> findByHeaderIdAndIsActiveTrueOrderByDisplayOrderAsc(UUID headerId);

    /**
     * Encuentra todas las tarjetas de un header por header_id ordenadas por display_order
     * @param headerId ID del header
     * @return lista de todas las tarjetas ordenadas
     */
    List<PageHeaderCards> findByHeaderIdOrderByDisplayOrderAsc(UUID headerId);


}
