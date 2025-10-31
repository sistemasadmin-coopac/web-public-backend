package com.elsalvador.coopac.service.admin.join.impl;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.entity.join.JoinSection;
import com.elsalvador.coopac.entity.join.JoinSpecialBenefit;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.join.JoinSectionRepository;
import com.elsalvador.coopac.repository.join.JoinSpecialBenefitRepository;
import com.elsalvador.coopac.service.admin.join.ManageJoinSpecialBenefitService;
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
 * Implementación del servicio para gestionar beneficios especiales de Join
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManageJoinSpecialBenefitServiceImpl implements ManageJoinSpecialBenefitService {

    private final JoinSpecialBenefitRepository specialBenefitRepository;
    private final JoinSectionRepository sectionRepository;

    @Override
    public JoinAdminDTO.JoinSpecialBenefitListDTO getAllSpecialBenefits() {
        log.info("Obteniendo todos los beneficios especiales");

        List<JoinSpecialBenefit> benefits = specialBenefitRepository.findAll().stream()
                .sorted(Comparator.comparing(b -> b.getDisplayOrder() != null ? b.getDisplayOrder() : 0))
                .collect(Collectors.toList());

        List<JoinAdminDTO.JoinSpecialBenefitDTO> dtos = benefits.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinSpecialBenefitListDTO(dtos, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitDTO getSpecialBenefitById(UUID id) {
        log.info("Obteniendo beneficio especial con ID: {}", id);
        JoinSpecialBenefit benefit = specialBenefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio especial no encontrado con ID: " + id));
        return mapToDTO(benefit);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getSpecialBenefitsBySection(UUID joinSectionId) {
        log.info("Obteniendo beneficios especiales para la sección con ID: {}", joinSectionId);

        List<JoinSpecialBenefit> benefits = specialBenefitRepository
                .findByJoinSectionIdOrderByDisplayOrderAsc(joinSectionId);

        return new JoinAdminDTO.JoinSpecialBenefitRequirementListDTO(null, benefits.size());
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitDTO createSpecialBenefit(JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto) {
        log.info("Creando nuevo beneficio especial con título: {}", dto.titleMain());

        // Obtener la primera sección (generalmente hay una)
        JoinSection section = sectionRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No hay secciones Join disponibles"));

        // Calcular el siguiente orden
        Integer nextOrder = specialBenefitRepository.findAll().stream()
                .mapToInt(b -> b.getDisplayOrder() != null ? b.getDisplayOrder() : 0)
                .max()
                .orElse(0) + 1;

        JoinSpecialBenefit benefit = JoinSpecialBenefit.builder()
                .joinSection(section)
                .titleMain(dto.titleMain())
                .subtitle(dto.subtitle())
                .fundTitle(dto.fundTitle())
                .fundDescription(dto.fundDescription())
                .benefitMaximum(dto.benefitMaximum())
                .maximumNote(dto.maximumNote())
                .annualAmount(dto.annualAmount())
                .annualNote(dto.annualNote())
                .displayOrder(nextOrder)
                .active(dto.active() != null ? dto.active() : true)
                .build();

        JoinSpecialBenefit saved = specialBenefitRepository.save(benefit);
        log.info("Beneficio especial creado con ID: {} - Orden: {}", saved.getId(), nextOrder);

        return mapToDTO(saved);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitDTO updateSpecialBenefit(UUID id, JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto) {
        log.info("Actualizando beneficio especial con ID: {}", id);

        JoinSpecialBenefit benefit = specialBenefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio especial no encontrado con ID: " + id));

        benefit.setTitleMain(dto.titleMain());
        benefit.setSubtitle(dto.subtitle());
        benefit.setFundTitle(dto.fundTitle());
        benefit.setFundDescription(dto.fundDescription());
        benefit.setBenefitMaximum(dto.benefitMaximum());
        benefit.setMaximumNote(dto.maximumNote());
        benefit.setAnnualAmount(dto.annualAmount());
        benefit.setAnnualNote(dto.annualNote());
        // displayOrder no se modifica, es gestionado por el backend
        benefit.setActive(dto.active() != null ? dto.active() : true);

        JoinSpecialBenefit updated = specialBenefitRepository.save(benefit);
        log.info("Beneficio especial actualizado con ID: {}", updated.getId());

        return mapToDTO(updated);

    }

    @Override
    public void deleteSpecialBenefit(UUID id) {
        log.info("Eliminando beneficio especial con ID: {}", id);

        JoinSpecialBenefit benefit = specialBenefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficio especial no encontrado con ID: " + id));

        specialBenefitRepository.delete(benefit);
        log.info("Beneficio especial eliminado con ID: {}", id);
    }

    /**
     * Mapea una entidad JoinSpecialBenefit a su DTO
     */
    private JoinAdminDTO.JoinSpecialBenefitDTO mapToDTO(JoinSpecialBenefit benefit) {
        return new JoinAdminDTO.JoinSpecialBenefitDTO(
                benefit.getId(),
                benefit.getTitleMain(),
                benefit.getSubtitle(),
                benefit.getFundTitle(),
                benefit.getFundDescription(),
                benefit.getBenefitMaximum(),
                benefit.getMaximumNote(),
                benefit.getAnnualAmount(),
                benefit.getAnnualNote(),
                benefit.getRequirements() != null ? benefit.getRequirements().stream()
                        .map(r -> new JoinAdminDTO.JoinSpecialBenefitRequirementDTO(
                                r.getId(),
                                r.getJoinSpecialBenefit().getId(),
                                r.getRequirementText(),
                                r.getRequirementOrder(),
                                r.getCreatedAt(),
                                r.getUpdatedAt()
                        ))
                        .collect(Collectors.toList()) : null,
                benefit.getDisplayOrder(),
                benefit.getActive()
        );
    }
}

