package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinRequirementGroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar grupos de requisitos de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/requirement-groups")
@RequiredArgsConstructor
public class JoinRequirementGroupController {

    private final ManageJoinRequirementGroupService managementService;

    /**
     * Obtiene todos los grupos de requisitos
     */
    @GetMapping
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupListDTO> getAllGroups() {
        JoinAdminDTO.JoinRequirementGroupListDTO groups = managementService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    /**
     * Obtiene un grupo de requisitos por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupDTO> getGroupById(@PathVariable UUID id) {
        JoinAdminDTO.JoinRequirementGroupDTO group = managementService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    /**
     * Actualiza un grupo de requisitos existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupDTO> updateGroup(
            @PathVariable UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto) {
        JoinAdminDTO.JoinRequirementGroupDTO updated = managementService.updateGroup(id, dto);
        return ResponseEntity.ok(updated);
    }
}

