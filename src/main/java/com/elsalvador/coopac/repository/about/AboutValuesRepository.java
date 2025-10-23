package com.elsalvador.coopac.repository.about;

import com.elsalvador.coopac.entity.about.AboutValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AboutValuesRepository extends JpaRepository<AboutValues, UUID> {

    /**
     * Obtiene todos los valores activos ordenados por display_order
     */
    List<AboutValues> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todos los valores ordenados por display_order para administración
     */
    List<AboutValues> findAllByOrderByDisplayOrderAsc();

    /**
     * Cuenta el número de valores activos
     */
    long countByIsActiveTrue();

    /**
     * Obtiene el máximo displayOrder de los valores
     * @return el valor máximo de displayOrder o 0 si no hay registros
     */
    @Query("SELECT COALESCE(MAX(v.displayOrder), 0) FROM AboutValues v")
    Integer findMaxDisplayOrder();
}
