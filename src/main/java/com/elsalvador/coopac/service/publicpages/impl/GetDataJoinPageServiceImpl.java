package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.join.JoinPageDTO;
import com.elsalvador.coopac.entity.join.*;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.join.JoinSectionRepository;
import com.elsalvador.coopac.service.publicpages.GetDataJoinPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataJoinPageServiceImpl implements GetDataJoinPageService {

    private final JoinSectionRepository joinSectionRepository;

    @Override
    public JoinPageDTO getJoinPageData() {
        log.debug("Obteniendo datos de página Join/Asóciate Ya");

        JoinSection joinSection = joinSectionRepository.findFirstByOrderByCreatedAtDesc()
            .orElseThrow(() -> {
                log.error("Página Join no configurada");
                return new EntityNotFoundException("Página Join no configurada");
            });

        return mapToPageDTO(joinSection);
    }

    private JoinPageDTO mapToPageDTO(JoinSection joinSection) {
        // Header
        JoinPageDTO.HeaderDTO header = null;
        if (joinSection.getPageHeader() != null) {
            header = new JoinPageDTO.HeaderDTO(
                joinSection.getPageHeader().getBadgeText(),
                joinSection.getPageHeader().getTitleMain(),
                joinSection.getPageHeader().getTitleHighlight(),
                joinSection.getPageHeader().getSubtitle()
            );
        }

        // Why Join Section
        JoinPageDTO.WhyJoinSectionDTO whyJoin = mapWhyJoinSection(joinSection.getBenefits());

        // Special Benefits Section
        List<JoinPageDTO.SpecialBenefitDTO> specialBenefits = mapSpecialBenefits(joinSection.getSpecialBenefits());

        // Cost Section
        JoinPageDTO.CostToJoinSectionDTO costToJoin = mapCostSection(joinSection.getCosts());

        // Requirements Section
        JoinPageDTO.RequirementsToJoinSectionDTO requirementsToJoin =
            mapRequirementsSection(joinSection.getRequirementGroups());

        // Visit Us Section
        JoinPageDTO.VisitUsSectionDTO visitUs = mapVisitUsSection();

        return new JoinPageDTO(header, whyJoin, specialBenefits, costToJoin, requirementsToJoin, visitUs);
    }

    private JoinPageDTO.WhyJoinSectionDTO mapWhyJoinSection(List<JoinBenefit> benefits) {
        List<JoinPageDTO.WhyJoinSectionDTO.JoinBenefitDTO> benefitDTOs = new ArrayList<>();

        if (benefits != null) {
            benefitDTOs = benefits.stream()
                .sorted(Comparator.comparing(b -> b.getSectionOrder() != null ? b.getSectionOrder() : 0))
                .map(this::mapBenefit)
                .collect(Collectors.toList());
        }

        return new JoinPageDTO.WhyJoinSectionDTO(
            "¿Por qué asociarte?",
            "Ser socio no es solo abrir una cuenta. Es pertenecer a una cooperativa que responde cuando más lo necesitas.",
            benefitDTOs
        );
    }

    private JoinPageDTO.WhyJoinSectionDTO.JoinBenefitDTO mapBenefit(JoinBenefit benefit) {
        return new JoinPageDTO.WhyJoinSectionDTO.JoinBenefitDTO(
            benefit.getTitle(),
            benefit.getDescription()
        );
    }

    private List<JoinPageDTO.SpecialBenefitDTO> mapSpecialBenefits(List<JoinSpecialBenefit> specialBenefits) {
        List<JoinPageDTO.SpecialBenefitDTO> specialBenefitDTOs = new ArrayList<>();

        if (specialBenefits != null && !specialBenefits.isEmpty()) {
            specialBenefitDTOs = specialBenefits.stream()
                .filter(benefit -> benefit.getActive() != null && benefit.getActive())
                .sorted(Comparator.comparing(b -> b.getDisplayOrder() != null ? b.getDisplayOrder() : 0))
                .map(this::mapSpecialBenefit)
                .collect(Collectors.toList());
        }

        return specialBenefitDTOs;
    }

    private JoinPageDTO.SpecialBenefitDTO mapSpecialBenefit(JoinSpecialBenefit specialBenefit) {
        List<String> requirementsList = new ArrayList<>();
        if (specialBenefit.getRequirements() != null) {
            requirementsList = specialBenefit.getRequirements().stream()
                .sorted(Comparator.comparing(r -> r.getRequirementOrder() != null ? r.getRequirementOrder() : 0))
                .map(JoinSpecialBenefitRequirement::getRequirementText)
                .collect(Collectors.toList());
        }

        return new JoinPageDTO.SpecialBenefitDTO(
            specialBenefit.getTitleMain(),
            specialBenefit.getSubtitle(),
            specialBenefit.getFundTitle(),
            specialBenefit.getFundDescription(),
            specialBenefit.getBenefitMaximum(),
            specialBenefit.getMaximumNote(),
            specialBenefit.getAnnualAmount(),
            specialBenefit.getAnnualNote(),
            requirementsList
        );
    }

    private JoinPageDTO.CostToJoinSectionDTO mapCostSection(List<JoinCost> costs) {
        List<JoinPageDTO.CostToJoinSectionDTO.CostItemDTO> items = new ArrayList<>();

        if (costs != null) {
            items = costs.stream()
                .sorted(Comparator.comparing(c -> c.getSectionOrder() != null ? c.getSectionOrder() : 0))
                .map(c -> new JoinPageDTO.CostToJoinSectionDTO.CostItemDTO(c.getLabel(), c.getAmount()))
                .collect(Collectors.toList());
        }

        return new JoinPageDTO.CostToJoinSectionDTO(
            "Aportes y Cuotas",
            "Esto es lo que necesitas para iniciar como socio.",
            items,
            "Estos aportes te permiten acceder a todos los servicios de la cooperativa."
        );
    }

    private JoinPageDTO.RequirementsToJoinSectionDTO mapRequirementsSection(
            List<JoinRequirementGroup> groups) {
        List<JoinPageDTO.RequirementsToJoinSectionDTO.RequirementGroupDTO> groupDTOs = new ArrayList<>();

        if (groups != null) {
            groupDTOs = groups.stream()
                .sorted(Comparator.comparing(g -> g.getSectionOrder() != null ? g.getSectionOrder() : 0))
                .map(g -> new JoinPageDTO.RequirementsToJoinSectionDTO.RequirementGroupDTO(
                    g.getGroupLabel(),
                    g.getItems()
                ))
                .collect(Collectors.toList());
        }

        return new JoinPageDTO.RequirementsToJoinSectionDTO(
            "Requisitos para Asociarte",
            "Documentos necesarios según la edad del solicitante.",
            groupDTOs
        );
    }

    private JoinPageDTO.VisitUsSectionDTO mapVisitUsSection() {
        JoinPageDTO.VisitUsSectionDTO.ContactDTO contact =
            new JoinPageDTO.VisitUsSectionDTO.ContactDTO(
                "Central / COOPERAFONO",
                "044-544011",
                "970003173"
            );

        List<JoinPageDTO.VisitUsSectionDTO.ActionDTO> actions = List.of(
            new JoinPageDTO.VisitUsSectionDTO.ActionDTO(
                "Llámanos",
                "phone",
                "044544011",
                false
            ),
            new JoinPageDTO.VisitUsSectionDTO.ActionDTO(
                "WhatsApp",
                "whatsapp",
                "https://wa.me/51970003173",
                true
            )
        );

        return new JoinPageDTO.VisitUsSectionDTO(
            "Ven a Afiliarte",
            "Te esperamos en nuestra oficina principal.",
            contact,
            actions
        );
    }
}

