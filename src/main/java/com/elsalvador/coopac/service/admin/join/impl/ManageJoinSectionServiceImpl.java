package com.elsalvador.coopac.service.admin.join.impl;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.entity.join.JoinSection;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.join.JoinSectionRepository;
import com.elsalvador.coopac.service.admin.join.ManageJoinSectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar secciones de Join
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManageJoinSectionServiceImpl implements ManageJoinSectionService {

    private final JoinSectionRepository sectionRepository;

    @Override
    public JoinAdminDTO.JoinSectionDTO getSectionById(UUID id) {
        log.info("Obteniendo sección de Join con ID: {}", id);
        JoinSection section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección de Join no encontrada con ID: " + id));
        return mapToDTO(section);
    }

    @Override
    public JoinAdminDTO.JoinSectionDTO updateSection(UUID id, JoinAdminDTO.JoinSectionDTO dto) {
        log.info("Actualizando sección de Join con ID: {}", id);

        JoinSection section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección de Join no encontrada con ID: " + id));

        JoinSection updated = sectionRepository.save(section);
        log.info("Sección de Join actualizada con ID: {}", updated.getId());

        return mapToDTO(updated);
    }

    /**
     * Mapea una entidad JoinSection a su DTO
     */
    private JoinAdminDTO.JoinSectionDTO mapToDTO(JoinSection section) {
        return new JoinAdminDTO.JoinSectionDTO(
                section.getId(),
                section.getPageHeader() != null ? section.getPageHeader().getId() : null,
                section.getBenefits() != null ? section.getBenefits().stream()
                        .map(b -> new JoinAdminDTO.JoinBenefitDTO(
                                b.getId(),
                                b.getTitle(),
                                b.getDescription(),
                                b.getSectionOrder()
                        ))
                        .collect(Collectors.toList()) : null,
                section.getCosts() != null ? section.getCosts().stream()
                        .map(c -> new JoinAdminDTO.JoinCostDTO(
                                c.getId(),
                                c.getLabel(),
                                c.getAmount(),
                                c.getSectionOrder()
                        ))
                        .collect(Collectors.toList()) : null,
                section.getRequirementGroups() != null ? section.getRequirementGroups().stream()
                        .map(rg -> new JoinAdminDTO.JoinRequirementGroupDTO(
                                rg.getId(),
                                rg.getGroupLabel(),
                                rg.getItems(),
                                rg.getSectionOrder()
                        ))
                        .collect(Collectors.toList()) : null,
                section.getSpecialBenefits() != null ? section.getSpecialBenefits().stream()
                        .map(sb -> new JoinAdminDTO.JoinSpecialBenefitDTO(
                                sb.getId(),
                                sb.getTitleMain(),
                                sb.getSubtitle(),
                                sb.getFundTitle(),
                                sb.getFundDescription(),
                                sb.getBenefitMaximum(),
                                sb.getMaximumNote(),
                                sb.getAnnualAmount(),
                                sb.getAnnualNote(),
                                sb.getRequirements() != null ? sb.getRequirements().stream()
                                        .map(r -> new JoinAdminDTO.JoinSpecialBenefitRequirementDTO(
                                                r.getId(),
                                                r.getJoinSpecialBenefit().getId(),
                                                r.getRequirementText(),
                                                r.getRequirementOrder(),
                                                r.getCreatedAt(),
                                                r.getUpdatedAt()
                                        ))
                                        .collect(Collectors.toList()) : null,
                                sb.getDisplayOrder(),
                                sb.getActive()
                        ))
                        .collect(Collectors.toList()) : null
        );
    }
}

