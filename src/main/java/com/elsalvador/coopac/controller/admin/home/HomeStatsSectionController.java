package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-stats-section")
@RequiredArgsConstructor
@Slf4j
public class HomeStatsSectionController {

    private final GetHomePromotionsService.HomeStatsSectionService homeStatsSectionService;

    /**
     * Obtiene todas las secciones de estadísticas
     */
    @GetMapping
    public ResponseEntity<List<HomeStatsSectionDTO>> getAllSections() {
        log.debug("GET /api/admin/home-stats-section - Obteniendo todas las secciones");
        List<HomeStatsSectionDTO> sections = homeStatsSectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    /**
     * Obtiene la sección activa
     */
    @GetMapping("/active")
    public ResponseEntity<HomeStatsSectionDTO> getActiveSection() {
        log.debug("GET /api/admin/home-stats-section/active - Obteniendo sección activa");
        HomeStatsSectionDTO section = homeStatsSectionService.getActiveSection();
        if (section != null) {
            return ResponseEntity.ok(section);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una sección por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<HomeStatsSectionDTO> getSectionById(@PathVariable UUID id) {
        log.debug("GET /api/admin/home-stats-section/{} - Obteniendo sección por ID", id);
        HomeStatsSectionDTO section = homeStatsSectionService.getSectionById(id);
        return ResponseEntity.ok(section);
    }

    /**
     * Crea una nueva sección de estadísticas
     */
    @PostMapping
    public ResponseEntity<HomeStatsSectionDTO> createSection(@Valid @RequestBody HomeStatsSectionDTO dto) {
        log.debug("POST /api/admin/home-stats-section - Creando nueva sección");
        HomeStatsSectionDTO createdSection = homeStatsSectionService.createSection(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSection);
    }

    /**
     * Actualiza una sección existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<HomeStatsSectionDTO> updateSection(
            @PathVariable UUID id,
            @Valid @RequestBody HomeStatsSectionDTO dto) {
        log.debug("PUT /api/admin/home-stats-section/{} - Actualizando sección", id);
        HomeStatsSectionDTO updatedSection = homeStatsSectionService.updateSection(id, dto);
        return ResponseEntity.ok(updatedSection);
    }

    /**
     * Elimina una sección de estadísticas
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable UUID id) {
        log.debug("DELETE /api/admin/home-stats-section/{} - Eliminando sección", id);
        homeStatsSectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }
}
