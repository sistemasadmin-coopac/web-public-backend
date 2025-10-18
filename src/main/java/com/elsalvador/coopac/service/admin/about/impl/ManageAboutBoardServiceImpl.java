package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.AboutBoardMembers;
import com.elsalvador.coopac.entity.about.AboutBoardSection;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.about.AboutBoardMembersRepository;
import com.elsalvador.coopac.repository.about.AboutBoardSectionRepository;
import com.elsalvador.coopac.service.admin.about.ManageAboutBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutBoardMemberDTO createBoardMember(AboutAdminDTO.AboutBoardMemberDTO dto) {
        log.info("Creando nuevo miembro de junta directiva: {}", dto.getFullName());

        AboutBoardMembers member = AboutBoardMembers.builder()
                .fullName(dto.getFullName())
                .position(dto.getPosition())
                .photoUrl(dto.getPhotoUrl())
                .bio(dto.getBio())
                .linkedinUrl(dto.getLinkedinUrl())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();

        AboutBoardMembers savedMember = boardMembersRepository.save(member);
        log.info("Miembro de junta directiva creado exitosamente con ID: {}", savedMember.getId());

        return mapToDTO(savedMember);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutBoardMemberDTO updateBoardMember(UUID id, AboutAdminDTO.AboutBoardMemberDTO dto) {
        log.info("Actualizando miembro de junta directiva con ID: {}", id);

        AboutBoardMembers existingMember = boardMembersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Miembro de junta directiva no encontrado con ID: " + id));

        existingMember.setFullName(dto.getFullName());
        existingMember.setPosition(dto.getPosition());
        existingMember.setPhotoUrl(dto.getPhotoUrl());
        existingMember.setBio(dto.getBio());
        existingMember.setLinkedinUrl(dto.getLinkedinUrl());
        existingMember.setEmail(dto.getEmail());
        existingMember.setPhone(dto.getPhone());
        existingMember.setDisplayOrder(dto.getDisplayOrder());
        existingMember.setIsActive(dto.getIsActive());

        AboutBoardMembers updatedMember = boardMembersRepository.save(existingMember);
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

    private AboutAdminDTO.AboutBoardMemberDTO mapToDTO(AboutBoardMembers member) {
        return AboutAdminDTO.AboutBoardMemberDTO.builder()
                .id(member.getId())
                .fullName(member.getFullName())
                .position(member.getPosition())
                .photoUrl(member.getPhotoUrl())
                .bio(member.getBio())
                .linkedinUrl(member.getLinkedinUrl())
                .email(member.getEmail())
                .phone(member.getPhone())
                .displayOrder(member.getDisplayOrder())
                .isActive(member.getIsActive())
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
