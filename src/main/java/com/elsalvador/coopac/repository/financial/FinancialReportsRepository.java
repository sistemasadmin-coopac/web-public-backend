package com.elsalvador.coopac.repository.financial;

import com.elsalvador.coopac.entity.financial.FinancialReportCategories;
import com.elsalvador.coopac.entity.financial.FinancialReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinancialReportsRepository extends JpaRepository<FinancialReports, UUID> {

    /**
     * Obtiene todos los reportes activos y públicos de una categoría ordenados por orden de visualización y fecha de publicación
     */
    List<FinancialReports> findByCategoryAndIsPublicTrueAndIsActiveTrueOrderByDisplayOrderAscPublishDateDesc(FinancialReportCategories category);

    /**
     * Cuenta los reportes activos y públicos por categoría
     */
    Long countByCategoryAndIsPublicTrueAndIsActiveTrue(FinancialReportCategories category);

    /**
     * Obtiene todos los reportes de una categoría (para administración)
     */
    List<FinancialReports> findByCategoryOrderByDisplayOrderAscPublishDateDesc(FinancialReportCategories category);

    /**
     * Obtiene todos los reportes ordenados
     */
    List<FinancialReports> findAllByOrderByDisplayOrderAscPublishDateDesc();

    /**
     * Busca un reporte por slug
     */
    Optional<FinancialReports> findBySlug(String slug);

    /**
     * Verifica si existe un reporte con el slug dado
     */
    boolean existsBySlug(String slug);

    /**
     * Verifica si existe un reporte con el slug dado, excluyendo un ID específico
     */
    boolean existsBySlugAndIdNot(String slug, UUID id);

    /**
     * Obtiene el máximo display_order actual
     */
    @Query("SELECT COALESCE(MAX(r.displayOrder), 0) FROM FinancialReports r")
    Integer findMaxDisplayOrder();

    /**
     * Cuenta reportes por categoría
     */
    Long countByCategory(FinancialReportCategories category);
}
