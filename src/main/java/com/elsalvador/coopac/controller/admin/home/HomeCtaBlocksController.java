package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomeCtaBlocksAdminDTO;
import com.elsalvador.coopac.service.admin.home.GetHomeCtaBlocksService;
import com.elsalvador.coopac.service.admin.home.UpdateHomeCtaBlocksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller para administración de bloques CTA del home
 */
@RestController
@RequestMapping("/api/admin/home-cta-blocks")
@RequiredArgsConstructor
public class HomeCtaBlocksController {

    private final GetHomeCtaBlocksService getHomeCtaBlocksService;
    private final UpdateHomeCtaBlocksService updateHomeCtaBlocksService;

    /**
     * Obtiene todos los bloques CTA activos
     */
    @GetMapping
    public ResponseEntity<List<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO>> getAllActiveCtaBlocks() {
        List<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO> ctaBlocks =
                getHomeCtaBlocksService.getAllActiveCtaBlocks();
        return ResponseEntity.ok(ctaBlocks);
    }

    /**
     * Actualiza un bloque CTA
     */
    @PutMapping("/{id}")
    public ResponseEntity<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO> updateCtaBlock(
            @PathVariable UUID id,
            @Valid @RequestBody HomeCtaBlocksAdminDTO.UpdateHomeCtaBlockDTO updateDTO) {
        HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO updatedCtaBlock =
                updateHomeCtaBlocksService.updateCtaBlock(id, updateDTO);
        return ResponseEntity.ok(updatedCtaBlock);
    }
}
