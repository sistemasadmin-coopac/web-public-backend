package com.elsalvador.coopac.service.admin.join.impl;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.entity.join.JoinRequirementGroup;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.join.JoinRequirementGroupRepository;
import com.elsalvador.coopac.service.admin.join.ManageJoinRequirementGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar grupos de requisitos de Join
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManageJoinRequirementGroupServiceImpl implements ManageJoinRequirementGroupService {

    private final JoinRequirementGroupRepository groupRepository;

    @Override
    public JoinAdminDTO.JoinRequirementGroupListDTO getAllGroups() {
        log.info("Obteniendo todos los grupos de requisitos");

        List<JoinRequirementGroup> groups = groupRepository.findAll().stream()
                .sorted(Comparator.comparing(g -> g.getSectionOrder() != null ? g.getSectionOrder() : 0))
                .collect(Collectors.toList());

        List<JoinAdminDTO.JoinRequirementGroupDTO> dtos = groups.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinRequirementGroupListDTO(dtos, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinRequirementGroupDTO getGroupById(UUID id) {
        log.info("Obteniendo grupo de requisitos con ID: {}", id);
        JoinRequirementGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo de requisitos no encontrado con ID: " + id));
        return mapToDTO(group);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getGroupsBySection(UUID joinSectionId) {
        log.info("Obteniendo grupos de requisitos para la sección con ID: {}", joinSectionId);

        List<JoinRequirementGroup> groups = groupRepository.findByJoinSectionIdOrderBySectionOrderAsc(joinSectionId);

        List<JoinAdminDTO.JoinRequirementGroupDTO> dtos = groups.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinSpecialBenefitRequirementListDTO(null, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinRequirementGroupDTO createGroup(JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto) {
        log.info("Creando nuevo grupo de requisitos con etiqueta: {}", dto.groupLabel());

        // Calcular el siguiente orden
        Integer nextOrder = groupRepository.findAll().stream()
                .mapToInt(g -> g.getSectionOrder() != null ? g.getSectionOrder() : 0)
                .max()
                .orElse(0) + 1;

        JoinRequirementGroup group = JoinRequirementGroup.builder()
                .groupLabel(dto.groupLabel())
                .items(dto.items())
                .sectionOrder(nextOrder)
                .build();

        JoinRequirementGroup saved = groupRepository.save(group);
        log.info("Grupo de requisitos creado con ID: {} - Orden: {}", saved.getId(), nextOrder);

        return mapToDTO(saved);
    }

    @Override
    public JoinAdminDTO.JoinRequirementGroupDTO updateGroup(UUID id, JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto) {
        log.info("Actualizando grupo de requisitos con ID: {}", id);

        JoinRequirementGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo de requisitos no encontrado con ID: " + id));

        group.setGroupLabel(dto.groupLabel());
        group.setItems(dto.items());
        // sectionOrder no se modifica, es gestionado por el backend

        JoinRequirementGroup updated = groupRepository.save(group);
        log.info("Grupo de requisitos actualizado con ID: {}", updated.getId());

        return mapToDTO(updated);
    }

    @Override
    public void deleteGroup(UUID id) {
        log.info("Eliminando grupo de requisitos con ID: {}", id);

        JoinRequirementGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo de requisitos no encontrado con ID: " + id));

        groupRepository.delete(group);
        log.info("Grupo de requisitos eliminado con ID: {}", id);
    }

    /**
     * Mapea una entidad JoinRequirementGroup a su DTO
     */
    private JoinAdminDTO.JoinRequirementGroupDTO mapToDTO(JoinRequirementGroup group) {
        return new JoinAdminDTO.JoinRequirementGroupDTO(
                group.getId(),
                group.getGroupLabel(),
                group.getItems(),
                group.getSectionOrder()
        );
    }
}

