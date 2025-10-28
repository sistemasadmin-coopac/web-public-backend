package com.elsalvador.coopac.dto.publicpage.join;

import java.util.List;

public record JoinPageDTO(
    HeaderDTO header,
    WhyJoinSectionDTO whyJoin,
    List<SpecialBenefitDTO> specialBenefits,
    CostToJoinSectionDTO costToJoin,
    RequirementsToJoinSectionDTO requirementsToJoin,
    VisitUsSectionDTO visitUs
) {

    public record HeaderDTO(
        String badgeText,
        String titleMain,
        String titleHighlight,
        String subtitle
    ) {}

    public record WhyJoinSectionDTO(
        String titleMain,
        String subtitle,
        List<JoinBenefitDTO> benefits
    ) {
        public record JoinBenefitDTO(
            String title,
            String description
        ) {}
    }

    public record SpecialBenefitDTO(
        String titleMain,
        String subtitle,
        String fundTitle,
        String fundDescription,
        String benefitMaximum,
        String maximumNote,
        String annualAmount,
        String annualNote,
        List<String> requirements
    ) {}

    public record CostToJoinSectionDTO(
        String titleMain,
        String subtitle,
        List<CostItemDTO> items,
        String note
    ) {
        public record CostItemDTO(
            String label,
            String amount
        ) {}
    }

    public record RequirementsToJoinSectionDTO(
        String titleMain,
        String subtitle,
        List<RequirementGroupDTO> groups
    ) {
        public record RequirementGroupDTO(
            String groupLabel,
            List<String> items
        ) {}
    }

    public record VisitUsSectionDTO(
        String titleMain,
        String subtitle,
        ContactDTO contact,
        List<ActionDTO> actions
    ) {
        public record ContactDTO(
            String phoneLabel,
            String phone,
            String whatsapp
        ) {}

        public record ActionDTO(
            String label,
            String type,
            String value,
            Boolean primary
        ) {}
    }
}

