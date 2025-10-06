package com.elsalvador.coopac.repository.financial;

import com.elsalvador.coopac.entity.financial.FinancialReportCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinancialReportCategoriesRepository extends JpaRepository<FinancialReportCategories, UUID> {

    /**
     * Obtiene todas las categorías de reportes activas ordenadas por orden de visualización
     */
    List<FinancialReportCategories> findByIsActiveTrueOrderByDisplayOrderAsc();

    /**
     * Obtiene todas las categorías ordenadas (para administración)
     */
    List<FinancialReportCategories> findAllByOrderByDisplayOrderAsc();

    /**
     * Busca una categoría por slug
     */
    Optional<FinancialReportCategories> findBySlug(String slug);

    /**
     * Verifica si existe una categoría con el slug dado
     */
    boolean existsBySlug(String slug);

    /**
     * Verifica si existe una categoría con el slug dado, excluyendo un ID específico
     */
    boolean existsBySlugAndIdNot(String slug, UUID id);

    /**
     * Obtiene el máximo display_order actual
     */
    @Query("SELECT COALESCE(MAX(c.displayOrder), 0) FROM FinancialReportCategories c")
    Integer findMaxDisplayOrder();
}
