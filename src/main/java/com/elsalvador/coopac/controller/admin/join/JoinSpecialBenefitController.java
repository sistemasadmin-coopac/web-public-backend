package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinSpecialBenefitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar beneficios especiales de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/special-benefits")
@RequiredArgsConstructor
public class JoinSpecialBenefitController {

    private final ManageJoinSpecialBenefitService managementService;

    /**
     * Obtiene todos los beneficios especiales
     */
    @GetMapping
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitListDTO> getAllSpecialBenefits() {
        JoinAdminDTO.JoinSpecialBenefitListDTO benefits = managementService.getAllSpecialBenefits();
        return ResponseEntity.ok(benefits);
    }

    /**
     * Obtiene un beneficio especial por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitDTO> getSpecialBenefitById(@PathVariable UUID id) {
        JoinAdminDTO.JoinSpecialBenefitDTO benefit = managementService.getSpecialBenefitById(id);
        return ResponseEntity.ok(benefit);
    }

    /**
     * Crea un nuevo beneficio especial
     */
    @PostMapping
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitDTO> createSpecialBenefit(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto) {
        JoinAdminDTO.JoinSpecialBenefitDTO created = managementService.createSpecialBenefit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un beneficio especial existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitDTO> updateSpecialBenefit(
            @PathVariable UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto) {
        JoinAdminDTO.JoinSpecialBenefitDTO updated = managementService.updateSpecialBenefit(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un beneficio especial
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialBenefit(@PathVariable UUID id) {
        managementService.deleteSpecialBenefit(id);
        return ResponseEntity.noContent().build();
    }
}

