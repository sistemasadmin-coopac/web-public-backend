package com.elsalvador.coopac.repository.navigation;

import com.elsalvador.coopac.entity.navigation.FooterColumns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FooterColumnsRepository extends JpaRepository<FooterColumns, UUID> {

    /**
     * Obtiene todas las columnas del footer activas con sus links ordenadas por orden de visualización
     */
    @Query("SELECT fc FROM FooterColumns fc LEFT JOIN FETCH fc.links fl WHERE fc.isActive = true ORDER BY fc.displayOrder ASC, fl.displayOrder ASC")
    List<FooterColumns> findByIsActiveTrueWithLinksOrderByDisplayOrderAsc();
}
