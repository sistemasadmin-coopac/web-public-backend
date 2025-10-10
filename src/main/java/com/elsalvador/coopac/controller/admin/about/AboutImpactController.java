package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutImpactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar métricas de impacto
 */
@RestController
@RequestMapping("/api/admin/about/impact")
@RequiredArgsConstructor
public class AboutImpactController {

    private final ManageAboutImpactService manageAboutImpactService;

    /**
     * Actualiza una métrica de impacto existente
     */
    @PutMapping("/metrics/{id}")
    public ResponseEntity<AboutAdminDTO.AboutImpactMetricDTO> updateImpactMetric(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutImpactMetricDTO dto) {
        AboutAdminDTO.AboutImpactMetricDTO updated = manageAboutImpactService.updateImpactMetric(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Actualiza la configuración de la sección de impacto
     */
    @PutMapping("/section")
    public ResponseEntity<AboutAdminDTO.AboutImpactSectionDTO> updateImpactSection(
            @Valid @RequestBody AboutAdminDTO.AboutImpactSectionDTO dto) {
        AboutAdminDTO.AboutImpactSectionDTO updated = manageAboutImpactService.updateImpactSection(dto);
        return ResponseEntity.ok(updated);
    }
}
