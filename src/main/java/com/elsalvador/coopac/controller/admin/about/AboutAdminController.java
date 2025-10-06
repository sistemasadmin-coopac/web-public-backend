package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.GetAboutAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para obtener datos completos de la página About
 */
@RestController
@RequestMapping("/api/admin/about")
@RequiredArgsConstructor
public class AboutAdminController {

    private final GetAboutAdminService getAboutAdminService;

    /**
     * Obtiene todos los datos completos de la página About para administración
     */
    @GetMapping("/complete")
    public ResponseEntity<AboutAdminDTO.AboutPageResponseDTO> getAboutCompleteData() {
        AboutAdminDTO.AboutPageResponseDTO aboutData = getAboutAdminService.getAboutCompleteData();
        return ResponseEntity.ok(aboutData);
    }
}
