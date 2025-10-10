package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.AboutMissionVision;
import com.elsalvador.coopac.repository.about.AboutMissionVisionRepository;
import com.elsalvador.coopac.service.admin.about.ManageAboutMissionVisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para gestionar misión y visión
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageAboutMissionVisionServiceImpl implements ManageAboutMissionVisionService {

    private final AboutMissionVisionRepository missionVisionRepository;

    @Override
    @Transactional
    public AboutAdminDTO.AboutMissionVisionDTO saveMissionVision(AboutAdminDTO.AboutMissionVisionDTO dto) {
        log.info("Creando o actualizando la configuración de misión y visión");

        // Buscar configuración existente activa
        AboutMissionVision existingConfig = missionVisionRepository.findFirstByIsActiveTrueOrderByUpdatedAtDesc()
                .orElse(null);

        AboutMissionVision missionVision;

        if (existingConfig != null) {
            // Actualizar configuración existente
            log.info("Actualizando configuración existente con ID: {}", existingConfig.getId());
            existingConfig.setMissionTitle(dto.getMissionTitle());
            existingConfig.setMissionText(dto.getMissionText());
            existingConfig.setVisionTitle(dto.getVisionTitle());
            existingConfig.setVisionText(dto.getVisionText());
            existingConfig.setIsActive(dto.getIsActive());

            missionVision = missionVisionRepository.save(existingConfig);
        } else {
            // Crear nueva configuración
            log.info("Creando nueva configuración de misión y visión");
            missionVision = AboutMissionVision.builder()
                    .missionTitle(dto.getMissionTitle())
                    .missionText(dto.getMissionText())
                    .visionTitle(dto.getVisionTitle())
                    .visionText(dto.getVisionText())
                    .isActive(dto.getIsActive())
                    .build();

            missionVision = missionVisionRepository.save(missionVision);
        }

        log.info("Configuración de misión y visión guardada exitosamente con ID: {}", missionVision.getId());
        return mapToDTO(missionVision);
    }

    private AboutAdminDTO.AboutMissionVisionDTO mapToDTO(AboutMissionVision missionVision) {
        return AboutAdminDTO.AboutMissionVisionDTO.builder()
                .id(missionVision.getId())
                .missionTitle(missionVision.getMissionTitle())
                .missionText(missionVision.getMissionText())
                .visionTitle(missionVision.getVisionTitle())
                .visionText(missionVision.getVisionText())
                .isActive(missionVision.getIsActive())
                .build();
    }
}
