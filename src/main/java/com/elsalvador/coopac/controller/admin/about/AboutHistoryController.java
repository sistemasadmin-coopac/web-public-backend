package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar historia y timeline de eventos
 */
@RestController
@RequestMapping("/api/admin/about/history")
@RequiredArgsConstructor
public class AboutHistoryController {

    private final ManageAboutHistoryService manageAboutHistoryService;

    /**
     * Crea un nuevo evento del timeline
     */
    @PostMapping("/timeline")
    public ResponseEntity<AboutAdminDTO.AboutTimelineEventDTO> createTimelineEvent(
            @Valid @RequestBody AboutAdminDTO.AboutTimelineEventDTO dto) {
        AboutAdminDTO.AboutTimelineEventDTO created = manageAboutHistoryService.createTimelineEvent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un evento del timeline existente
     */
    @PutMapping("/timeline/{id}")
    public ResponseEntity<AboutAdminDTO.AboutTimelineEventDTO> updateTimelineEvent(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutTimelineEventDTO dto) {
        AboutAdminDTO.AboutTimelineEventDTO updated = manageAboutHistoryService.updateTimelineEvent(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un evento del timeline
     */
    @DeleteMapping("/timeline/{id}")
    public ResponseEntity<Void> deleteTimelineEvent(@PathVariable UUID id) {
        manageAboutHistoryService.deleteTimelineEvent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza la configuración de la sección de historia
     */
    @PutMapping("/section")
    public ResponseEntity<AboutAdminDTO.AboutHistorySectionDTO> updateHistorySection(
            @Valid @RequestBody AboutAdminDTO.AboutHistorySectionDTO dto) {
        AboutAdminDTO.AboutHistorySectionDTO updated = manageAboutHistoryService.updateHistorySection(dto);
        return ResponseEntity.ok(updated);
    }
}
