package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-promotions")
@RequiredArgsConstructor
public class HomePromotionsController {

    private final GetHomePromotionsService getService;
    private final ManageHomePromotionsService manageService;

    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<HomePromotionsAdminDTO>> getPromotionsBySection(@PathVariable UUID sectionId) {
        List<HomePromotionsAdminDTO> promotions = getService.getPromotionsBySection(sectionId);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/section/{sectionId}/all")
    public ResponseEntity<List<HomePromotionsAdminDTO>> getAllPromotionsBySection(@PathVariable UUID sectionId) {
        List<HomePromotionsAdminDTO> promotions = getService.getAllPromotionsBySection(sectionId);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomePromotionsAdminDTO> getPromotionById(@PathVariable UUID id) {
        HomePromotionsAdminDTO promotion = getService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<HomePromotionsAdminDTO> createPromotion(
            @Valid @RequestBody HomePromotionsAdminDTO promotionDTO) {
        HomePromotionsAdminDTO createdPromotion = manageService.createPromotion(promotionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
    }

    @PutMapping
    public ResponseEntity<HomePromotionsAdminDTO> updatePromotion(
            @Valid @RequestBody HomePromotionsAdminDTO promotionDTO) {
        HomePromotionsAdminDTO updatedPromotion = manageService.updatePromotion(promotionDTO);
        return ResponseEntity.ok(updatedPromotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable UUID id) {
        manageService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<HomePromotionsAdminDTO> activatePromotion(@PathVariable UUID id) {
        HomePromotionsAdminDTO activatedPromotion = manageService.activatePromotion(id);
        return ResponseEntity.ok(activatedPromotion);
    }
}
