package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutBoardMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AboutBoardMembersRepository extends JpaRepository<AboutBoardMembers, UUID> {

    /**
     * Obtiene todos los miembros de junta directiva activos ordenados por display_order
     */
    List<AboutBoardMembers> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todos los miembros ordenados por display_order para administración
     */
    List<AboutBoardMembers> findAllByOrderByDisplayOrderAsc();

    /**
     * Cuenta el número de miembros activos
     */
    long countByIsActiveTrue();
}
