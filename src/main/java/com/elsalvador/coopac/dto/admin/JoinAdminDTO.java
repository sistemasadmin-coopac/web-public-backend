package com.elsalvador.coopac.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * DTOs para la gestión de secciones Join/Asóciate Ya
 */
public class JoinAdminDTO {

    /**
     * DTO para JoinSpecialBenefitRequirement
     */
    public record JoinSpecialBenefitRequirementDTO(
            UUID id,
            @NotNull(message = "El ID del beneficio especial es requerido")
            UUID joinSpecialBenefitId,
            @NotBlank(message = "El texto del requisito es requerido")
            String requirementText,
            @NotNull(message = "El orden del requisito es requerido")
            Integer requirementOrder,
            Long createdAt,
            Long updatedAt
    ) {}

    /**
     * DTO para crear/actualizar JoinSpecialBenefitRequirement
     */
    public record CreateUpdateJoinSpecialBenefitRequirementDTO(
            @NotNull(message = "El ID del beneficio especial es requerido")
            UUID joinSpecialBenefitId,
            @NotBlank(message = "El texto del requisito es requerido")
            String requirementText
    ) {}

    /**
     * DTO para listar requisitos por beneficio especial
     */
    public record JoinSpecialBenefitRequirementListDTO(
            List<JoinSpecialBenefitRequirementDTO> requirements,
            Integer total
    ) {}

    /**
     * DTO para JoinBenefit
     */
    public record JoinBenefitDTO(
            UUID id,
            @NotBlank(message = "El título es requerido")
            String title,
            @NotBlank(message = "La descripción es requerida")
            String description,
            @NotNull(message = "El orden de la sección es requerido")
            Integer sectionOrder
    ) {}

    /**
     * DTO para crear/actualizar JoinBenefit
     */
    public record CreateUpdateJoinBenefitDTO(
            @NotBlank(message = "El título es requerido")
            String title,
            @NotBlank(message = "La descripción es requerida")
            String description
    ) {}

    /**
     * DTO para JoinCost
     */
    public record JoinCostDTO(
            UUID id,
            @NotBlank(message = "La etiqueta es requerida")
            String label,
            @NotBlank(message = "El monto es requerido")
            String amount,
            @NotNull(message = "El orden de la sección es requerido")
            Integer sectionOrder
    ) {}

    /**
     * DTO para crear/actualizar JoinCost
     */
    public record CreateUpdateJoinCostDTO(
            @NotBlank(message = "La etiqueta es requerida")
            String label,
            @NotBlank(message = "El monto es requerido")
            String amount
    ) {}

    /**
     * DTO para JoinRequirementGroup
     */
    public record JoinRequirementGroupDTO(
            UUID id,
            @NotBlank(message = "La etiqueta del grupo es requerida")
            String groupLabel,
            List<String> items,
            @NotNull(message = "El orden de la sección es requerido")
            Integer sectionOrder
    ) {}

    /**
     * DTO para crear/actualizar JoinRequirementGroup
     */
    public record CreateUpdateJoinRequirementGroupDTO(
            @NotBlank(message = "La etiqueta del grupo es requerida")
            String groupLabel,
            List<String> items
    ) {}

    /**
     * DTO para JoinSection
     */
    public record JoinSectionDTO(
            UUID id,
            UUID pageHeaderId,
            List<JoinBenefitDTO> benefits,
            List<JoinCostDTO> costs,
            List<JoinRequirementGroupDTO> requirementGroups,
            List<JoinSpecialBenefitDTO> specialBenefits
    ) {}

    /**
     * DTO para JoinSpecialBenefit
     */
    public record JoinSpecialBenefitDTO(
            UUID id,
            @NotBlank(message = "El título principal es requerido")
            String titleMain,
            String subtitle,
            String fundTitle,
            String fundDescription,
            String benefitMaximum,
            String maximumNote,
            String annualAmount,
            String annualNote,
            List<JoinSpecialBenefitRequirementDTO> requirements,
            @NotNull(message = "El orden de visualización es requerido")
            Integer displayOrder,
            Boolean active
    ) {}

    /**
     * DTO para crear/actualizar JoinSpecialBenefit
     */
    public record CreateUpdateJoinSpecialBenefitDTO(
            @NotBlank(message = "El título principal es requerido")
            String titleMain,
            String subtitle,
            String fundTitle,
            String fundDescription,
            String benefitMaximum,
            String maximumNote,
            String annualAmount,
            String annualNote,
            Boolean active
    ) {}

    /**
     * DTO para listar beneficios
     */
    public record JoinBenefitListDTO(
            List<JoinBenefitDTO> benefits,
            Integer total
    ) {}

    /**
     * DTO para listar costos
     */
    public record JoinCostListDTO(
            List<JoinCostDTO> costs,
            Integer total
    ) {}

    /**
     * DTO para listar grupos de requisitos
     */
    public record JoinRequirementGroupListDTO(
            List<JoinRequirementGroupDTO> groups,
            Integer total
    ) {}

    /**
     * DTO para listar beneficios especiales
     */
    public record JoinSpecialBenefitListDTO(
            List<JoinSpecialBenefitDTO> specialBenefits,
            Integer total
    ) {}
}

