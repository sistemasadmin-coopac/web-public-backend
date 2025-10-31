package com.elsalvador.coopac.service.admin.join.impl;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.entity.join.JoinSpecialBenefit;
import com.elsalvador.coopac.entity.join.JoinSpecialBenefitRequirement;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.join.JoinSpecialBenefitRepository;
import com.elsalvador.coopac.repository.join.JoinSpecialBenefitRequirementRepository;
import com.elsalvador.coopac.service.admin.join.ManageJoinSpecialBenefitRequirementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar requisitos de beneficios especiales de Join
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManageJoinSpecialBenefitRequirementServiceImpl implements ManageJoinSpecialBenefitRequirementService {

    private final JoinSpecialBenefitRequirementRepository requirementRepository;
    private final JoinSpecialBenefitRepository benefitRepository;

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementDTO getRequirementById(UUID id) {
        log.info("Obteniendo requisito de beneficio especial con ID: {}", id);
        JoinSpecialBenefitRequirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requisito de beneficio especial no encontrado con ID: " + id));
        return mapToDTO(requirement);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getAllRequirements() {
        log.info("Obteniendo todos los requisitos de beneficios especiales");

        List<JoinSpecialBenefitRequirement> requirements = requirementRepository.findAll();

        List<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> dtos = requirements.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinSpecialBenefitRequirementListDTO(dtos, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getRequirementsByBenefit(UUID joinSpecialBenefitId) {
        log.info("Obteniendo requisitos para el beneficio especial con ID: {}", joinSpecialBenefitId);

        // Validar que el beneficio existe
        benefitRepository.findById(joinSpecialBenefitId)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio especial no encontrado con ID: " + joinSpecialBenefitId));

        List<JoinSpecialBenefitRequirement> requirements = requirementRepository
                .findByJoinSpecialBenefitIdOrderByRequirementOrderAsc(joinSpecialBenefitId);

        List<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> dtos = requirements.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinSpecialBenefitRequirementListDTO(dtos, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementDTO createRequirement(JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto) {
        log.info("Creando nuevo requisito para el beneficio especial con ID: {}", dto.joinSpecialBenefitId());

        JoinSpecialBenefit benefit = benefitRepository.findById(dto.joinSpecialBenefitId())
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio especial no encontrado con ID: " + dto.joinSpecialBenefitId()));

        // Calcular el siguiente orden
        Integer nextOrder = requirementRepository.findByJoinSpecialBenefitIdOrderByRequirementOrderAsc(dto.joinSpecialBenefitId())
                .stream()
                .mapToInt(r -> r.getRequirementOrder() != null ? r.getRequirementOrder() : 0)
                .max()
                .orElse(0) + 1;

        JoinSpecialBenefitRequirement requirement = JoinSpecialBenefitRequirement.builder()
                .joinSpecialBenefit(benefit)
                .requirementText(dto.requirementText())
                .requirementOrder(nextOrder)
                .build();

        JoinSpecialBenefitRequirement saved = requirementRepository.save(requirement);
        log.info("Requisito de beneficio especial creado con ID: {} - Orden: {}", saved.getId(), nextOrder);

        return mapToDTO(saved);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementDTO updateRequirement(UUID id, JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto) {
        log.info("Actualizando requisito de beneficio especial con ID: {}", id);

        JoinSpecialBenefitRequirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requisito de beneficio especial no encontrado con ID: " + id));

        JoinSpecialBenefit benefit = benefitRepository.findById(dto.joinSpecialBenefitId())
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio especial no encontrado con ID: " + dto.joinSpecialBenefitId()));

        requirement.setJoinSpecialBenefit(benefit);
        requirement.setRequirementText(dto.requirementText());
        // requirementOrder no se modifica, es gestionado por el backend

        JoinSpecialBenefitRequirement updated = requirementRepository.save(requirement);
        log.info("Requisito de beneficio especial actualizado con ID: {}", updated.getId());

        return mapToDTO(updated);
    }

    @Override
    public void deleteRequirement(UUID id) {
        log.info("Eliminando requisito de beneficio especial con ID: {}", id);

        JoinSpecialBenefitRequirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requisito de beneficio especial no encontrado con ID: " + id));

        requirementRepository.delete(requirement);
        log.info("Requisito de beneficio especial eliminado con ID: {}", id);
    }

    /**
     * Mapea una entidad JoinSpecialBenefitRequirement a su DTO
     */
    private JoinAdminDTO.JoinSpecialBenefitRequirementDTO mapToDTO(JoinSpecialBenefitRequirement requirement) {
        return new JoinAdminDTO.JoinSpecialBenefitRequirementDTO(
                requirement.getId(),
                requirement.getJoinSpecialBenefit().getId(),
                requirement.getRequirementText(),
                requirement.getRequirementOrder(),
                requirement.getCreatedAt(),
                requirement.getUpdatedAt()
        );
    }
}

