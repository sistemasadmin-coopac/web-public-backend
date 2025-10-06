package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutBoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar miembros de junta directiva
 */
@RestController
@RequestMapping("/api/admin/about/board")
@RequiredArgsConstructor
public class AboutBoardController {

    private final ManageAboutBoardService manageAboutBoardService;

    /**
     * Crea un nuevo miembro de junta directiva
     */
    @PostMapping("/members")
    public ResponseEntity<AboutAdminDTO.AboutBoardMemberDTO> createBoardMember(
            @Valid @RequestBody AboutAdminDTO.AboutBoardMemberDTO dto) {
        AboutAdminDTO.AboutBoardMemberDTO created = manageAboutBoardService.createBoardMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un miembro de junta directiva existente
     */
    @PutMapping("/members/{id}")
    public ResponseEntity<AboutAdminDTO.AboutBoardMemberDTO> updateBoardMember(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutBoardMemberDTO dto) {
        AboutAdminDTO.AboutBoardMemberDTO updated = manageAboutBoardService.updateBoardMember(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un miembro de junta directiva
     */
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteBoardMember(@PathVariable UUID id) {
        manageAboutBoardService.deleteBoardMember(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza la configuración de la sección de junta directiva
     */
    @PutMapping("/section")
    public ResponseEntity<AboutAdminDTO.AboutBoardSectionDTO> updateBoardSection(
            @Valid @RequestBody AboutAdminDTO.AboutBoardSectionDTO dto) {
        AboutAdminDTO.AboutBoardSectionDTO updated = manageAboutBoardService.updateBoardSection(dto);
        return ResponseEntity.ok(updated);
    }
}
