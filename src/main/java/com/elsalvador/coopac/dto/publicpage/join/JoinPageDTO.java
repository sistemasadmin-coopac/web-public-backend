package com.elsalvador.coopac.dto.publicpage.join;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Datos completos de la pagina Join/Asociate Ya")
public record JoinPageDTO(
    @Schema(description = "Encabezado de la pagina")
    HeaderDTO header,
    @Schema(description = "Seccion: Por que asociarse")
    WhyJoinSectionDTO whyJoin,
    @Schema(description = "Beneficios especiales disponibles")
    List<SpecialBenefitDTO> specialBenefits,
    @Schema(description = "Seccion: Costos para asociarse")
    CostToJoinSectionDTO costToJoin,
    @Schema(description = "Seccion: Requisitos para asociarse")
    RequirementsToJoinSectionDTO requirementsToJoin,
    @Schema(description = "Seccion: Horarios de atencion")
    ScheduleSectionDTO schedule,
    @Schema(description = "Seccion: Visitanos")
    VisitUsSectionDTO visitUs
) {

    @Schema(description = "Header de la pagina")
    public record HeaderDTO(
        @Schema(description = "Texto del badge", example = "Asociate Hoy")
        String badgeText,
        @Schema(description = "Titulo principal")
        String titleMain,
        @Schema(description = "Parte destacada del titulo")
        String titleHighlight,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle
    ) {}

    @Schema(description = "Seccion: Por que asociarse")
    public record WhyJoinSectionDTO(
        @Schema(description = "Titulo principal de la seccion")
        String titleMain,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle,
        @Schema(description = "Beneficios principales para asociarse")
        List<JoinBenefitDTO> benefits
    ) {
        @Schema(description = "Item de beneficio")
        public record JoinBenefitDTO(
            @Schema(description = "Titulo del beneficio")
            String title,
            @Schema(description = "Descripcion del beneficio")
            String description,
            @Schema(description = "Icono del beneficio")
            String icon
        ) {}
    }

    @Schema(description = "Beneficio especial")
    public record SpecialBenefitDTO(
        @Schema(description = "Titulo principal del beneficio")
        String titleMain,
        @Schema(description = "Subtitulo del beneficio")
        String subtitle,
        @Schema(description = "Titulo del fondo")
        String fundTitle,
        @Schema(description = "Descripcion del fondo")
        String fundDescription,
        @Schema(description = "Beneficio maximo disponible")
        String benefitMaximum,
        @Schema(description = "Nota sobre el maximo")
        String maximumNote,
        @Schema(description = "Monto anual")
        String annualAmount,
        @Schema(description = "Nota sobre el monto anual")
        String annualNote,
        @Schema(description = "Requisitos para acceder al beneficio")
        List<String> requirements
    ) {}

    @Schema(description = "Seccion: Costos para asociarse")
    public record CostToJoinSectionDTO(
        @Schema(description = "Titulo principal de la seccion")
        String titleMain,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle,
        @Schema(description = "Items de costos")
        List<CostItemDTO> items,
        @Schema(description = "Nota general sobre los costos")
        String note
    ) {
        @Schema(description = "Item de costo")
        public record CostItemDTO(
            @Schema(description = "Etiqueta del costo", example = "Cuota de Asociacion")
            String label,
            @Schema(description = "Monto del costo", example = "$25.00")
            String amount
        ) {}
    }

    @Schema(description = "Seccion: Requisitos para asociarse")
    public record RequirementsToJoinSectionDTO(
        @Schema(description = "Titulo principal de la seccion")
        String titleMain,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle,
        @Schema(description = "Grupos de requisitos")
        List<RequirementGroupDTO> groups
    ) {
        @Schema(description = "Grupo de requisitos")
        public record RequirementGroupDTO(
            @Schema(description = "Etiqueta del grupo", example = "Documentacion Requerida")
            String groupLabel,
            @Schema(description = "Items del grupo")
            List<String> items
        ) {}
    }

    @Schema(description = "Seccion: Horarios de atencion")
    public record ScheduleSectionDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Items de horario")
        List<ScheduleItemDTO> items,
        @Schema(description = "Nota adicional sobre horarios")
        String note
    ) {
        @Schema(description = "Item de horario")
        public record ScheduleItemDTO(
            @Schema(description = "Etiqueta del horario", example = "Lunes a Viernes")
            String label,
            @Schema(description = "Hora de apertura", example = "08:00")
            String open,
            @Schema(description = "Hora de cierre", example = "17:00")
            String close,
            @Schema(description = "Indica si esta cerrado")
            Boolean isClosed,
            @Schema(description = "Orden de aparicion")
            Integer order
        ) {}
    }

    @Schema(description = "Seccion: Visitanos")
    public record VisitUsSectionDTO(
        @Schema(description = "Titulo principal")
        String titleMain,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle,
        @Schema(description = "Informacion de contacto")
        ContactDTO contact,
        @Schema(description = "Acciones disponibles")
        List<ActionDTO> actions
    ) {
        @Schema(description = "Informacion de contacto")
        public record ContactDTO(
            @Schema(description = "Etiqueta para el telefono")
            String phoneLabel,
            @Schema(description = "Numero de telefono")
            String phone,
            @Schema(description = "Numero de WhatsApp")
            String whatsapp
        ) {}

        @Schema(description = "Accion disponible")
        public record ActionDTO(
            @Schema(description = "Etiqueta de la accion")
            String label,
            @Schema(description = "Tipo de accion", example = "link")
            String type,
            @Schema(description = "Valor de la accion (URL o ID)")
            String value,
            @Schema(description = "Indica si es accion primaria")
            Boolean primary
        ) {}
    }
}

