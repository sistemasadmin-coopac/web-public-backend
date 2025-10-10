package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutMissionVisionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para gestionar misión y visión
 */
@RestController
@RequestMapping("/api/admin/about/mission-vision")
@RequiredArgsConstructor
public class AboutMissionVisionController {

    private final ManageAboutMissionVisionService manageAboutMissionVisionService;

    /**
     * Actualiza la configuración de misión y visión
     */
    @PutMapping
    public ResponseEntity<AboutAdminDTO.AboutMissionVisionDTO> updateMissionVision(
            @Valid @RequestBody AboutAdminDTO.AboutMissionVisionDTO dto) {
        AboutAdminDTO.AboutMissionVisionDTO updated = manageAboutMissionVisionService.saveMissionVision(dto);
        return ResponseEntity.ok(updated);
    }
}
