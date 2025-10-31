package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinSpecialBenefitRequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar requisitos de beneficios especiales de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/special-benefits/requirements")
@RequiredArgsConstructor
public class JoinSpecialBenefitRequirementController {

    private final ManageJoinSpecialBenefitRequirementService managementService;

    /**
     * Obtiene un requisito de beneficio especial por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> getRequirementById(@PathVariable UUID id) {
        JoinAdminDTO.JoinSpecialBenefitRequirementDTO requirement = managementService.getRequirementById(id);
        return ResponseEntity.ok(requirement);
    }

    /**
     * Obtiene todos los requisitos de beneficios especiales
     */
    @GetMapping
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementListDTO> getAllRequirements() {
        JoinAdminDTO.JoinSpecialBenefitRequirementListDTO requirements = managementService.getAllRequirements();
        return ResponseEntity.ok(requirements);
    }

    /**
     * Obtiene todos los requisitos de un beneficio especial
     */
    @GetMapping("/benefit/{joinSpecialBenefitId}")
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementListDTO> getRequirementsByBenefit(
            @PathVariable UUID joinSpecialBenefitId) {
        JoinAdminDTO.JoinSpecialBenefitRequirementListDTO requirements = managementService.getRequirementsByBenefit(joinSpecialBenefitId);
        return ResponseEntity.ok(requirements);
    }

    /**
     * Crea un nuevo requisito para un beneficio especial
     */
    @PostMapping
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> createRequirement(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto) {
        JoinAdminDTO.JoinSpecialBenefitRequirementDTO created = managementService.createRequirement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un requisito de beneficio especial existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> updateRequirement(
            @PathVariable UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto) {
        JoinAdminDTO.JoinSpecialBenefitRequirementDTO updated = managementService.updateRequirement(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un requisito de beneficio especial
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequirement(@PathVariable UUID id) {
        managementService.deleteRequirement(id);
        return ResponseEntity.noContent().build();
    }
}

