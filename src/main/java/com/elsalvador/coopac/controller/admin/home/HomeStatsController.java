package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-stats")
@RequiredArgsConstructor
@Slf4j
public class HomeStatsController {

    private final GetHomePromotionsService.HomeStatsService homeStatsService;

    /**
     * Obtiene todas las estadísticas
     */
    @GetMapping
    public ResponseEntity<List<HomeStatsDTO>> getAllStats() {
        log.debug("GET /api/admin/home-stats - Obteniendo todas las estadísticas");
        List<HomeStatsDTO> stats = homeStatsService.getAllStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Obtiene las estadísticas activas
     */
    @GetMapping("/active")
    public ResponseEntity<List<HomeStatsDTO>> getActiveStats() {
        log.debug("GET /api/admin/home-stats/active - Obteniendo estadísticas activas");
        List<HomeStatsDTO> stats = homeStatsService.getActiveStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Obtiene una estadística por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<HomeStatsDTO> getStatsById(@PathVariable UUID id) {
        log.debug("GET /api/admin/home-stats/{} - Obteniendo estadística por ID", id);
        HomeStatsDTO stats = homeStatsService.getStatsById(id);
        return ResponseEntity.ok(stats);
    }

    /**
     * Actualiza una estadística existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<HomeStatsDTO> updateStats(
            @PathVariable UUID id,
            @Valid @RequestBody HomeStatsDTO dto) {
        log.debug("PUT /api/admin/home-stats/{} - Actualizando estadística", id);
        HomeStatsDTO updatedStats = homeStatsService.updateStats(id, dto);
        return ResponseEntity.ok(updatedStats);
    }
}
