package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsSectionService;
import com.elsalvador.coopac.service.admin.home.UpdateHomePromotionsSectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-promotions-section")
@RequiredArgsConstructor
public class HomePromotionsSectionController {

    private final GetHomePromotionsSectionService getService;
    private final UpdateHomePromotionsSectionService updateService;

    @GetMapping("/active")
    public ResponseEntity<HomePromotionsSectionAdminDTO> getActivePromotionsSection() {
        HomePromotionsSectionAdminDTO section = getService.getActivePromotionsSection();
        return ResponseEntity.ok(section);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomePromotionsSectionAdminDTO> getPromotionsSectionById(@PathVariable UUID id) {
        HomePromotionsSectionAdminDTO section = getService.getPromotionsSectionById(id);
        return ResponseEntity.ok(section);
    }

    @PutMapping
    public ResponseEntity<HomePromotionsSectionAdminDTO> updatePromotionsSection(
            @Valid @RequestBody HomePromotionsSectionAdminDTO sectionDTO) {
        HomePromotionsSectionAdminDTO updatedSection = updateService.updatePromotionsSection(sectionDTO);
        return ResponseEntity.ok(updatedSection);
    }
}
