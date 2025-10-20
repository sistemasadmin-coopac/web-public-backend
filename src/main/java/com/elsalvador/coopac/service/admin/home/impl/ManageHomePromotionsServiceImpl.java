package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.repository.HomePromotionsRepository;
import com.elsalvador.coopac.repository.HomePromotionsSectionRepository;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionsService;
import com.elsalvador.coopac.service.admin.home.mapper.HomePromotionsAdminMapper;
import com.elsalvador.coopac.service.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.elsalvador.coopac.config.CacheConfig.HOME_PAGE_CACHE;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageHomePromotionsServiceImpl implements ManageHomePromotionsService {

    private final HomePromotionsRepository repository;
    private final HomePromotionsSectionRepository sectionRepository;
    private final HomePromotionsAdminMapper mapper;
    private final FileStorageService fileStorageService;

    private static final String FOLDER = "home-promotions";

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomePromotionsAdminDTO createPromotion(HomePromotionsAdminDTO promotionDTO, MultipartFile image) {
        log.info("Creando nueva promoción para sección ID: {}", promotionDTO.sectionId());

        // Verificar que la sección existe
        Optional<HomePromotionsSection> section = sectionRepository.findById(promotionDTO.sectionId());
        if (section.isEmpty()) {
            log.warn("Sección de promociones no encontrada con ID: {}", promotionDTO.sectionId());
            throw new RuntimeException("Promotions section not found with ID: " + promotionDTO.sectionId());
        }

        HomePromotions newPromotion = HomePromotions.builder()
                .section(section.get())
                .title(promotionDTO.title())
                .tag(promotionDTO.tag())
                .description(promotionDTO.description())
                .highlightText(promotionDTO.highlightText())
                .isFeatured(promotionDTO.isFeatured() != null ? promotionDTO.isFeatured() : false)
                .displayOrder(promotionDTO.displayOrder() != null ? promotionDTO.displayOrder() : 0)
                .isActive(promotionDTO.isActive() != null ? promotionDTO.isActive() : true)
                .build();

        HomePromotions savedPromotion = repository.save(newPromotion);
        log.info("Promoción creada exitosamente con ID: {}", savedPromotion.getId());

        // Almacenar imagen si se proporcionó
        if (image != null && !image.isEmpty()) {
            try {
                fileStorageService.storeFileWithName(image, FOLDER, savedPromotion.getId().toString());
                log.info("Imagen almacenada para promoción ID: {}", savedPromotion.getId());
            } catch (Exception e) {
                log.error("Error al almacenar imagen para promoción ID: {}", savedPromotion.getId(), e);
                // No lanzamos excepción para no fallar toda la operación
            }
        }

        return mapper.toDTO(savedPromotion);
    }

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomePromotionsAdminDTO updatePromotion(HomePromotionsAdminDTO promotionDTO, MultipartFile image) {
        log.info("Actualizando promoción con ID: {}", promotionDTO.id());

        Optional<HomePromotions> promotionDb = repository.findById(promotionDTO.id());
        if (promotionDb.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionDTO.id());
            throw new RuntimeException("Promotion not found with ID: " + promotionDTO.id());
        }

        HomePromotions existingPromotion = promotionDb.get();

        // Verificar y actualizar la sección solo si se proporciona en el DTO
        if (promotionDTO.sectionId() != null) {
            Optional<HomePromotionsSection> sectionOpt = sectionRepository.findById(promotionDTO.sectionId());
            if (sectionOpt.isEmpty()) {
                log.warn("Sección de promociones no encontrada con ID: {}", promotionDTO.sectionId());
                throw new RuntimeException("Promotions section not found with ID: " + promotionDTO.sectionId());
            }
            existingPromotion.setSection(sectionOpt.get());
        }
        // Si sectionId es null, mantener la sección actual (no actualizar)

        // Actualizar los demás campos
        if (promotionDTO.title() != null) {
            existingPromotion.setTitle(promotionDTO.title());
        }
        if (promotionDTO.tag() != null) {
            existingPromotion.setTag(promotionDTO.tag());
        }
        if (promotionDTO.description() != null) {
            existingPromotion.setDescription(promotionDTO.description());
        }
        if (promotionDTO.highlightText() != null) {
            existingPromotion.setHighlightText(promotionDTO.highlightText());
        }
        if (promotionDTO.isFeatured() != null) {
            existingPromotion.setIsFeatured(promotionDTO.isFeatured());
        }
        if (promotionDTO.displayOrder() != null) {
            existingPromotion.setDisplayOrder(promotionDTO.displayOrder());
        }
        if (promotionDTO.isActive() != null) {
            existingPromotion.setIsActive(promotionDTO.isActive());
        }

        HomePromotions savedPromotion = repository.save(existingPromotion);

        // Gestionar imagen - solo actualizar si se proporciona una nueva
        if (image != null && !image.isEmpty()) {
            try {
                // Eliminar imagen anterior si existe
                deletePromotionImage(savedPromotion.getId());

                // Almacenar nueva imagen
                fileStorageService.storeFileWithName(image, FOLDER, savedPromotion.getId().toString());
                log.info("Imagen actualizada para promoción ID: {}", savedPromotion.getId());
            } catch (Exception e) {
                log.error("Error al actualizar imagen para promoción ID: {}", savedPromotion.getId(), e);
            }
        }
        // Si image es null o vacío, mantenemos la imagen anterior sin cambios

        log.info("Promoción actualizada exitosamente con ID: {}", savedPromotion.getId());
        return mapper.toDTO(savedPromotion);
    }

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public void deletePromotion(UUID promotionId) {
        log.info("Eliminando promoción con ID: {}", promotionId);

        Optional<HomePromotions> promotionDb = repository.findById(promotionId);
        if (promotionDb.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionId);
            throw new RuntimeException("Promotion not found with ID: " + promotionId);
        }

        // Eliminar imagen de Storage antes de eliminar el registro
        deletePromotionImage(promotionId);

        // Eliminar físicamente el registro de la base de datos
        repository.delete(promotionDb.get());

        log.info("Promoción eliminada exitosamente con ID: {}", promotionId);
    }

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomePromotionsAdminDTO activatePromotion(UUID promotionId) {
        log.info("Activando promoción con ID: {}", promotionId);

        Optional<HomePromotions> promotionDb = repository.findById(promotionId);
        if (promotionDb.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionId);
            throw new RuntimeException("Promotion not found with ID: " + promotionId);
        }

        promotionDb.get().setIsActive(true);
        HomePromotions savedPromotion = repository.save(promotionDb.get());

        log.info("Promoción activada exitosamente con ID: {}", promotionId);
        return mapper.toDTO(savedPromotion);
    }

    /**
     * Método auxiliar para eliminar imagen de una promoción
     */
    private void deletePromotionImage(UUID promotionId) {
        try {
            // Buscar y eliminar imagen con extensiones comunes
            String[] extensions = {".jpg", ".jpeg", ".png", ".webp"};
            for (String ext : extensions) {
                String fileName = promotionId.toString() + ext;
                String fileUrl = fileStorageService.getFileUrl(fileName, FOLDER);
                if (fileStorageService.fileExists(fileUrl)) {
                    fileStorageService.deleteFile(fileUrl);
                    log.info("Imagen eliminada para promoción ID: {} ({})", promotionId, fileName);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Error al eliminar imagen para promoción ID: {}", promotionId, e);
        }
    }
}
