package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinBenefitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar beneficios de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/benefits")
@RequiredArgsConstructor
public class JoinBenefitController {

    private final ManageJoinBenefitService managementService;

    /**
     * Obtiene todos los beneficios
     */
    @GetMapping
    public ResponseEntity<JoinAdminDTO.JoinBenefitListDTO> getAllBenefits() {
        JoinAdminDTO.JoinBenefitListDTO benefits = managementService.getAllBenefits();
        return ResponseEntity.ok(benefits);
    }

    /**
     * Crea un nuevo beneficio
     */
    @PostMapping
    public ResponseEntity<JoinAdminDTO.JoinBenefitDTO> createBenefit(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinBenefitDTO dto) {
        JoinAdminDTO.JoinBenefitDTO created = managementService.createBenefit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtiene un beneficio por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinBenefitDTO> getBenefitById(@PathVariable UUID id) {
        JoinAdminDTO.JoinBenefitDTO benefit = managementService.getBenefitById(id);
        return ResponseEntity.ok(benefit);
    }

    /**
     * Actualiza un beneficio existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinBenefitDTO> updateBenefit(
            @PathVariable UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinBenefitDTO dto) {
        JoinAdminDTO.JoinBenefitDTO updated = managementService.updateBenefit(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un beneficio existente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable UUID id) {
        managementService.deleteBenefit(id);
        return ResponseEntity.noContent().build();
    }
}

