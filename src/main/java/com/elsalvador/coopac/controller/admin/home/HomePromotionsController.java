package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-promotions")
@RequiredArgsConstructor
public class HomePromotionsController {

    private final GetHomePromotionsService getService;
    private final ManageHomePromotionsService manageService;
    private final ObjectMapper objectMapper;

    // Tamaño máximo de imagen: 2MB
    private static final long MAX_IMAGE_SIZE = 2 * 1024 * 1024; // 2MB en bytes

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HomePromotionsAdminDTO> createPromotion(
            @RequestPart("promotion") String promotionJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            // Validar tamaño de imagen
            validateImageSize(image);

            HomePromotionsAdminDTO promotionDTO = objectMapper.readValue(promotionJson, HomePromotionsAdminDTO.class);
            HomePromotionsAdminDTO createdPromotion = manageService.createPromotion(promotionDTO, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HomePromotionsAdminDTO> updatePromotion(
            @RequestPart("promotion") String promotionJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            // Validar tamaño de imagen
            validateImageSize(image);

            HomePromotionsAdminDTO promotionDTO = objectMapper.readValue(promotionJson, HomePromotionsAdminDTO.class);
            HomePromotionsAdminDTO updatedPromotion = manageService.updatePromotion(promotionDTO, image);
            return ResponseEntity.ok(updatedPromotion);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
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

    /**
     * Valida que la imagen no exceda el tamaño máximo permitido (2MB)
     * @param image Archivo de imagen a validar
     * @throws IllegalArgumentException si la imagen excede el tamaño máximo
     */
    private void validateImageSize(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            long sizeInBytes = image.getSize();
            if (sizeInBytes > MAX_IMAGE_SIZE) {
                double sizeInMB = sizeInBytes / (1024.0 * 1024.0);
                throw new IllegalArgumentException(
                    String.format("La imagen excede el tamaño máximo permitido de 2MB. Tamaño actual: %.2f MB", sizeInMB)
                );
            }
        }
    }
}
