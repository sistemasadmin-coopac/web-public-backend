package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutBoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(value = "/members", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AboutAdminDTO.AboutBoardMemberDTO> createBoardMember(
            @Valid @ModelAttribute AboutAdminDTO.AboutBoardMemberDTO dto,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        AboutAdminDTO.AboutBoardMemberDTO created = manageAboutBoardService.createBoardMember(dto, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un miembro de junta directiva existente
     */
    @PutMapping(value = "/members/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AboutAdminDTO.AboutBoardMemberDTO> updateBoardMember(
            @PathVariable UUID id,
            @Valid @ModelAttribute AboutAdminDTO.AboutBoardMemberDTO dto,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        AboutAdminDTO.AboutBoardMemberDTO updated = manageAboutBoardService.updateBoardMember(id, dto, photo);
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
