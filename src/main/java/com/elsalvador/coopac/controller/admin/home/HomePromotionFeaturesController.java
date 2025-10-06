package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionFeaturesAdminDTO;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionFeaturesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-promotion-features")
@RequiredArgsConstructor
public class HomePromotionFeaturesController {

    private final ManageHomePromotionFeaturesService manageService;

    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity<List<HomePromotionFeaturesAdminDTO>> getFeaturesByPromotion(@PathVariable UUID promotionId) {
        List<HomePromotionFeaturesAdminDTO> features = manageService.getFeaturesByPromotion(promotionId);
        return ResponseEntity.ok(features);
    }

    @PostMapping
    public ResponseEntity<HomePromotionFeaturesAdminDTO> createFeature(
            @Valid @RequestBody HomePromotionFeaturesAdminDTO featureDTO) {
        HomePromotionFeaturesAdminDTO createdFeature = manageService.createFeature(featureDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeature);
    }

    @PutMapping
    public ResponseEntity<HomePromotionFeaturesAdminDTO> updateFeature(
            @Valid @RequestBody HomePromotionFeaturesAdminDTO featureDTO) {
        HomePromotionFeaturesAdminDTO updatedFeature = manageService.updateFeature(featureDTO);
        return ResponseEntity.ok(updatedFeature);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable UUID id) {
        manageService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/promotion/{promotionId}/all")
    public ResponseEntity<List<HomePromotionFeaturesAdminDTO>> updatePromotionFeatures(
            @PathVariable UUID promotionId,
            @Valid @RequestBody List<HomePromotionFeaturesAdminDTO> features) {
        List<HomePromotionFeaturesAdminDTO> updatedFeatures =
            manageService.updatePromotionFeatures(promotionId, features);
        return ResponseEntity.ok(updatedFeatures);
    }
}
