package com.elsalvador.coopac.repository.navigation;

import com.elsalvador.coopac.entity.navigation.NavigationMenus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NavigationMenusRepository extends JpaRepository<NavigationMenus, UUID> {

    /**
     * Obtiene un menú activo por su slug con sus items relacionados
     */
    @Query("SELECT m FROM NavigationMenus m LEFT JOIN FETCH m.items i WHERE m.slug = :slug AND m.isActive = true ORDER BY i.displayOrder ASC")
    Optional<NavigationMenus> findBySlugAndIsActiveTrueWithItems(@Param("slug") String slug);
}
