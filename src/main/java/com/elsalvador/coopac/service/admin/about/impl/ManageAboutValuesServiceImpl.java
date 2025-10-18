package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.AboutValues;
import com.elsalvador.coopac.entity.about.AboutValuesSection;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.about.AboutValuesRepository;
import com.elsalvador.coopac.repository.about.AboutValuesSectionRepository;
import com.elsalvador.coopac.service.admin.about.ManageAboutValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.ABOUT_PAGE_CACHE;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar valores organizacionales
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageAboutValuesServiceImpl implements ManageAboutValuesService {

    private final AboutValuesRepository valuesRepository;
    private final AboutValuesSectionRepository valuesSectionRepository;

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutValueDTO createValue(AboutAdminDTO.AboutValueDTO dto) {
        log.info("Creando nuevo valor: {}", dto.getTitle());

        AboutValues value = AboutValues.builder()
                .icon(dto.getIcon())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();

        AboutValues savedValue = valuesRepository.save(value);
        log.info("Valor creado exitosamente con ID: {}", savedValue.getId());

        return mapValueToDTO(savedValue);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutValueDTO updateValue(UUID id, AboutAdminDTO.AboutValueDTO dto) {
        log.info("Actualizando valor con ID: {}", id);

        AboutValues existingValue = valuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Valor no encontrado con ID: " + id));

        existingValue.setIcon(dto.getIcon());
        existingValue.setTitle(dto.getTitle());
        existingValue.setDescription(dto.getDescription());
        existingValue.setDisplayOrder(dto.getDisplayOrder());
        existingValue.setIsActive(dto.getIsActive());

        AboutValues updatedValue = valuesRepository.save(existingValue);
        log.info("Valor actualizado exitosamente");

        return mapValueToDTO(updatedValue);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public void deleteValue(UUID id) {
        log.info("Eliminando valor con ID: {}", id);

        AboutValues value = valuesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Valor no encontrado con ID: " + id));

        valuesRepository.delete(value);
        log.info("Valor eliminado exitosamente");
    }

    @Override
    public AboutAdminDTO.AboutValuesSectionDTO getValuesSection() {
        log.info("Obteniendo configuración de la sección de valores");

        AboutValuesSection section = valuesSectionRepository.findFirstByOrderByUpdatedAtDesc()
                .orElse(createDefaultValuesSection());

        return mapSectionToDTO(section);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutValuesSectionDTO updateValuesSection(AboutAdminDTO.AboutValuesSectionDTO dto) {
        log.info("Actualizando configuración de la sección de valores");

        AboutValuesSection section = valuesSectionRepository.findFirstByOrderByUpdatedAtDesc()
                .orElse(createDefaultValuesSection());

        section.setTitle(dto.getTitle());
        section.setSubtitle(dto.getSubtitle());
        section.setIsActive(dto.getIsActive());

        AboutValuesSection updatedSection = valuesSectionRepository.save(section);
        log.info("Configuración de la sección de valores actualizada exitosamente");

        return mapSectionToDTO(updatedSection);
    }

    private AboutValuesSection createDefaultValuesSection() {
        return AboutValuesSection.builder()
                .title("Nuestros Valores")
                .subtitle("Los principios que guían nuestro trabajo y compromiso con la comunidad")
                .isActive(true)
                .build();
    }

    private AboutAdminDTO.AboutValueDTO mapValueToDTO(AboutValues value) {
        return AboutAdminDTO.AboutValueDTO.builder()
                .id(value.getId())
                .icon(value.getIcon())
                .title(value.getTitle())
                .description(value.getDescription())
                .displayOrder(value.getDisplayOrder())
                .isActive(value.getIsActive())
                .build();
    }

    private AboutAdminDTO.AboutValuesSectionDTO mapSectionToDTO(AboutValuesSection section) {
        return AboutAdminDTO.AboutValuesSectionDTO.builder()
                .id(section.getId())
                .title(section.getTitle())
                .subtitle(section.getSubtitle())
                .isActive(section.getIsActive())
                .build();
    }
}
