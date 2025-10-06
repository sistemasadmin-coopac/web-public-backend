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
public class HomeStatsSectionDTO {

    private UUID id;

    @NotBlank(message = "El título es requerido")
    @Size(max = 500, message = "El título no puede exceder 500 caracteres")
    private String title;

    private String subtitle;

    @NotNull(message = "El estado activo es requerido")
    private Boolean isActive;
}
