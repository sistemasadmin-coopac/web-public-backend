package com.elsalvador.coopac.dto.admin;

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
public class HomeStatsDTO {

    private UUID id;

    @NotBlank(message = "El label es requerido")
    @Size(max = 255, message = "El label no puede exceder 255 caracteres")
    private String label;

    @NotBlank(message = "El valor de texto es requerido")
    @Size(max = 50, message = "El valor de texto no puede exceder 50 caracteres")
    private String valueText;

    @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
    private String icon;

    @NotNull(message = "El orden de visualización es requerido")
    private Integer displayOrder;

    @NotNull(message = "El estado activo es requerido")
    private Boolean isActive;
}
