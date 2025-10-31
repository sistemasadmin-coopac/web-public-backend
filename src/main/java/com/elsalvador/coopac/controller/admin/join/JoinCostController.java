package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinCostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar costos de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/costs")
@RequiredArgsConstructor
public class JoinCostController {

    private final ManageJoinCostService managementService;

    /**
     * Obtiene todos los costos
     */
    @GetMapping
    public ResponseEntity<JoinAdminDTO.JoinCostListDTO> getAllCosts() {
        JoinAdminDTO.JoinCostListDTO costs = managementService.getAllCosts();
        return ResponseEntity.ok(costs);
    }

    /**
     * Obtiene un costo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinCostDTO> getCostById(@PathVariable UUID id) {
        JoinAdminDTO.JoinCostDTO cost = managementService.getCostById(id);
        return ResponseEntity.ok(cost);
    }

    /**
     * Actualiza un costo existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinCostDTO> updateCost(
            @PathVariable UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinCostDTO dto) {
        JoinAdminDTO.JoinCostDTO updated = managementService.updateCost(id, dto);
        return ResponseEntity.ok(updated);
    }
}

