package com.elsalvador.coopac.service.publicpages;

import org.springframework.core.io.Resource;

import java.util.UUID;

/**
 * Servicio para descargar reportes financieros públicos
 */
public interface DownloadFinancialReportService {

    /**
     * Obtiene el recurso de archivo para descargar un reporte financiero
     *
     * @param reportId ID del reporte
     * @return Resource con el archivo del reporte
     */
    Resource downloadReport(UUID reportId);

    /**
     * Obtiene el nombre del archivo con extensión
     *
     * @param reportId ID del reporte
     * @return Nombre del archivo con extensión
     */
    String getFileName(UUID reportId);
}

