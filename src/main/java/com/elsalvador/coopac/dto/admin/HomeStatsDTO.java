package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estadística del home")
public class HomeStatsDTO {

    @Schema(description = "ID único de la estadística", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotBlank(message = "El label es requerido")
    @Size(max = 255, message = "El label no puede exceder 255 caracteres")
    @Schema(description = "Etiqueta o nombre de la estadística", example = "Clientes Satisfechos")
    private String label;

    @NotBlank(message = "El valor de texto es requerido")
    @Size(max = 50, message = "El valor de texto no puede exceder 50 caracteres")
    @Schema(description = "Valor numérico como texto", example = "10000+")
    private String valueText;

    @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
    @Schema(description = "Icono de la estadística", example = "heart, star, check")
    private String icon;

    @NotNull(message = "El estado activo es requerido")
    @Schema(description = "Si la estadística está activa", example = "true")
    private Boolean isActive;
}
