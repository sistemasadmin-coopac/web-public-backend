package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutValuesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar valores organizacionales
 */
@RestController
@RequestMapping("/api/admin/about/values")
@RequiredArgsConstructor
public class AboutValuesController {

    private final ManageAboutValuesService manageAboutValuesService;

    /**
     * Crea un nuevo valor
     */
    @PostMapping
    public ResponseEntity<AboutAdminDTO.AboutValueDTO> createValue(
            @Valid @RequestBody AboutAdminDTO.AboutValueDTO dto) {
        AboutAdminDTO.AboutValueDTO created = manageAboutValuesService.createValue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un valor existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<AboutAdminDTO.AboutValueDTO> updateValue(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutValueDTO dto) {
        AboutAdminDTO.AboutValueDTO updated = manageAboutValuesService.updateValue(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un valor
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValue(@PathVariable UUID id) {
        manageAboutValuesService.deleteValue(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Actualiza la configuración de la sección de valores
     */
    @PutMapping("/section")
    public ResponseEntity<AboutAdminDTO.AboutValuesSectionDTO> updateValuesSection(
            @Valid @RequestBody AboutAdminDTO.AboutValuesSectionDTO dto) {
        AboutAdminDTO.AboutValuesSectionDTO updated = manageAboutValuesService.updateValuesSection(dto);
        return ResponseEntity.ok(updated);
    }
}
