package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.AboutBoardMembers;
import com.elsalvador.coopac.entity.about.AboutBoardSection;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.about.AboutBoardMembersRepository;
import com.elsalvador.coopac.repository.about.AboutBoardSectionRepository;
import com.elsalvador.coopac.service.admin.about.ManageAboutBoardService;
import com.elsalvador.coopac.service.storage.FileStorageService;
import com.elsalvador.coopac.util.ImagePlaceholderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.elsalvador.coopac.config.CacheConfig.ABOUT_PAGE_CACHE;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar miembros de junta directiva
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageAboutBoardServiceImpl implements ManageAboutBoardService {

    private final AboutBoardMembersRepository boardMembersRepository;
    private final AboutBoardSectionRepository boardSectionRepository;
    private final FileStorageService fileStorageService;

    private static final String FOLDER = "about-board-members";

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutBoardMemberDTO createBoardMember(AboutAdminDTO.AboutBoardMemberDTO dto, MultipartFile photo) {
        log.info("Creando nuevo miembro de junta directiva: {}", dto.getFullName());

        AboutBoardMembers member = AboutBoardMembers.builder()
                .fullName(dto.getFullName())
                .position(dto.getPosition())
                .bio(dto.getBio())
                .linkedinUrl(dto.getLinkedinUrl())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();

        AboutBoardMembers savedMember = boardMembersRepository.save(member);
        log.info("Miembro de junta directiva creado exitosamente con ID: {}", savedMember.getId());

        // Almacenar foto si se proporcionó
        if (photo != null && !photo.isEmpty()) {
            try {
                fileStorageService.storeFileWithName(photo, FOLDER, savedMember.getId().toString());
                log.info("Foto almacenada para miembro ID: {}", savedMember.getId());
            } catch (Exception e) {
                log.error("Error al almacenar foto para miembro ID: {}", savedMember.getId(), e);
            }
        }

        return mapToDTO(savedMember);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutBoardMemberDTO updateBoardMember(UUID id, AboutAdminDTO.AboutBoardMemberDTO dto, MultipartFile photo) {
        log.info("Actualizando miembro de junta directiva con ID: {}", id);

        AboutBoardMembers existingMember = boardMembersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miembro de junta directiva no encontrado con ID: " + id));

        // Actualizar campos solo si vienen en el DTO
        if (dto.getFullName() != null) {
            existingMember.setFullName(dto.getFullName());
        }
        if (dto.getPosition() != null) {
            existingMember.setPosition(dto.getPosition());
        }
        if (dto.getBio() != null) {
            existingMember.setBio(dto.getBio());
        }
        if (dto.getLinkedinUrl() != null) {
            existingMember.setLinkedinUrl(dto.getLinkedinUrl());
        }
        if (dto.getEmail() != null) {
            existingMember.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            existingMember.setPhone(dto.getPhone());
        }
        if (dto.getDisplayOrder() != null) {
            existingMember.setDisplayOrder(dto.getDisplayOrder());
        }
        if (dto.getIsActive() != null) {
            existingMember.setIsActive(dto.getIsActive());
        }

        AboutBoardMembers updatedMember = boardMembersRepository.save(existingMember);

        // Gestionar foto - solo actualizar si se proporciona una nueva
        if (photo != null && !photo.isEmpty()) {
            try {
                // Eliminar foto anterior si existe
                deleteMemberPhoto(id);

                // Almacenar nueva foto
                fileStorageService.storeFileWithName(photo, FOLDER, id.toString());
                log.info("Foto actualizada para miembro ID: {}", id);
            } catch (Exception e) {
                log.error("Error al actualizar foto para miembro ID: {}", id, e);
            }
        }

        log.info("Miembro de junta directiva actualizado exitosamente");

        return mapToDTO(updatedMember);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public void deleteBoardMember(UUID id) {
        log.info("Eliminando miembro de junta directiva con ID: {}", id);

        AboutBoardMembers member = boardMembersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miembro de junta directiva no encontrado con ID: " + id));

        // Eliminar foto de Storage antes de eliminar el registro
        deleteMemberPhoto(id);

        // Eliminar físicamente el registro
        boardMembersRepository.delete(member);

        log.info("Miembro de junta directiva eliminado exitosamente");
    }

    @Override
    public AboutAdminDTO.AboutBoardSectionDTO getBoardSection() {
        log.info("Obteniendo configuración de la sección de junta directiva");

        AboutBoardSection section = boardSectionRepository.findFirstByOrderByUpdatedAtDesc()
                .orElse(createDefaultBoardSection());

        return mapSectionToDTO(section);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutBoardSectionDTO updateBoardSection(AboutAdminDTO.AboutBoardSectionDTO dto) {
        log.info("Actualizando configuración de la sección de junta directiva");

        AboutBoardSection section = boardSectionRepository.findFirstByOrderByUpdatedAtDesc()
                .orElse(createDefaultBoardSection());

        section.setTitle(dto.getTitle());
        section.setSubtitle(dto.getSubtitle());
        section.setIsActive(dto.getIsActive());

        AboutBoardSection updatedSection = boardSectionRepository.save(section);
        log.info("Configuración de la sección de junta directiva actualizada exitosamente");

        return mapSectionToDTO(updatedSection);
    }

    private AboutBoardSection createDefaultBoardSection() {
        return AboutBoardSection.builder()
                .title("Junta Directiva")
                .subtitle("Conoce a los miembros de nuestra junta directiva")
                .isActive(true)
                .build();
    }

    /**
     * Método auxiliar para eliminar foto de un miembro
     */
    private void deleteMemberPhoto(UUID memberId) {
        try {
            // Buscar y eliminar foto con extensiones comunes
            String[] extensions = {".jpg", ".jpeg", ".png", ".webp"};
            for (String ext : extensions) {
                String fileName = memberId.toString() + ext;
                String fileUrl = fileStorageService.getFileUrl(fileName, FOLDER);
                if (fileStorageService.fileExists(fileUrl)) {
                    fileStorageService.deleteFile(fileUrl);
                    log.info("Foto eliminada para miembro ID: {} ({})", memberId, fileName);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Error al eliminar foto para miembro ID: {}", memberId, e);
        }
    }

    private AboutAdminDTO.AboutBoardMemberDTO mapToDTO(AboutBoardMembers member) {
        // Obtener la foto desde Storage como Base64
        String photoBase64 = null;
        try {
            String[] extensions = {".jpg", ".jpeg", ".png", ".webp"};
            for (String ext : extensions) {
                String fileName = member.getId().toString() + ext;
                photoBase64 = fileStorageService.getFileAsBase64(fileName, FOLDER);
                if (photoBase64 != null) {
                    break;
                }
            }
        } catch (Exception e) {
            log.debug("No se encontró foto para miembro ID: {}", member.getId());
        }

        // Si no hay foto, usar placeholder genérico
        if (photoBase64 == null) {
            photoBase64 = ImagePlaceholderUtil.PROMOTION_PLACEHOLDER;
        }

        return AboutAdminDTO.AboutBoardMemberDTO.builder()
                .id(member.getId())
                .fullName(member.getFullName())
                .position(member.getPosition())
                .bio(member.getBio())
                .linkedinUrl(member.getLinkedinUrl())
                .email(member.getEmail())
                .phone(member.getPhone())
                .displayOrder(member.getDisplayOrder())
                .isActive(member.getIsActive())
                .photoBase64(photoBase64)
                .build();
    }

    private AboutAdminDTO.AboutBoardSectionDTO mapSectionToDTO(AboutBoardSection section) {
        return AboutAdminDTO.AboutBoardSectionDTO.builder()
                .id(section.getId())
                .title(section.getTitle())
                .subtitle(section.getSubtitle())
                .isActive(section.getIsActive())
                .build();
    }
}
