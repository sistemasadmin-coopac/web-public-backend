package com.elsalvador.coopac.service.admin.join.impl;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.entity.join.JoinBenefit;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.join.JoinBenefitRepository;
import com.elsalvador.coopac.service.admin.join.ManageJoinBenefitService;
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
 * Implementación del servicio para gestionar beneficios de Join
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManageJoinBenefitServiceImpl implements ManageJoinBenefitService {

    private final JoinBenefitRepository benefitRepository;

    @Override
    public JoinAdminDTO.JoinBenefitListDTO getAllBenefits() {
        log.info("Obteniendo todos los beneficios");

        List<JoinBenefit> benefits = benefitRepository.findAll().stream()
                .sorted(Comparator.comparing(b -> b.getSectionOrder() != null ? b.getSectionOrder() : 0))
                .collect(Collectors.toList());

        List<JoinAdminDTO.JoinBenefitDTO> dtos = benefits.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinBenefitListDTO(dtos, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinBenefitDTO getBenefitById(UUID id) {
        log.info("Obteniendo beneficio con ID: {}", id);
        JoinBenefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));
        return mapToDTO(benefit);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getBenefitsBySection(UUID joinSectionId) {
        log.info("Obteniendo beneficios para la sección con ID: {}", joinSectionId);

        List<JoinBenefit> benefits = benefitRepository.findByJoinSectionIdOrderBySectionOrderAsc(joinSectionId);

        List<JoinAdminDTO.JoinBenefitDTO> dtos = benefits.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinSpecialBenefitRequirementListDTO(null, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinBenefitDTO createBenefit(JoinAdminDTO.CreateUpdateJoinBenefitDTO dto) {
        log.info("Creando nuevo beneficio con título: {}", dto.title());

        // Calcular el siguiente orden
        Integer nextOrder = benefitRepository.findAll().stream()
                .mapToInt(b -> b.getSectionOrder() != null ? b.getSectionOrder() : 0)
                .max()
                .orElse(0) + 1;

        JoinBenefit benefit = JoinBenefit.builder()
                .title(dto.title())
                .description(dto.description())
                .sectionOrder(nextOrder)
                .build();

        JoinBenefit saved = benefitRepository.save(benefit);
        log.info("Beneficio creado con ID: {} - Orden: {}", saved.getId(), nextOrder);

        return mapToDTO(saved);
    }

    @Override
    public JoinAdminDTO.JoinBenefitDTO updateBenefit(UUID id, JoinAdminDTO.CreateUpdateJoinBenefitDTO dto) {
        log.info("Actualizando beneficio con ID: {}", id);

        JoinBenefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));

        benefit.setTitle(dto.title());
        benefit.setDescription(dto.description());
        // sectionOrder no se modifica, es gestionado por el backend

        JoinBenefit updated = benefitRepository.save(benefit);
        log.info("Beneficio actualizado con ID: {}", updated.getId());

        return mapToDTO(updated);
    }

    @Override
    public void deleteBenefit(UUID id) {
        log.info("Eliminando beneficio con ID: {}", id);

        JoinBenefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio no encontrado con ID: " + id));

        benefitRepository.delete(benefit);
        log.info("Beneficio eliminado con ID: {}", id);
    }

    /**
     * Mapea una entidad JoinBenefit a su DTO
     */
    private JoinAdminDTO.JoinBenefitDTO mapToDTO(JoinBenefit benefit) {
        return new JoinAdminDTO.JoinBenefitDTO(
                benefit.getId(),
                benefit.getTitle(),
                benefit.getDescription(),
                benefit.getSectionOrder()
        );
    }
}

