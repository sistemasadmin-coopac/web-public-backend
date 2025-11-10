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
@Schema(description = "Sección de estadísticas del home")
public class HomeStatsSectionDTO {

    @Schema(description = "ID único de la sección", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotBlank(message = "El título es requerido")
    @Size(max = 500, message = "El título no puede exceder 500 caracteres")
    @Schema(description = "Título de la sección", example = "Nuestros Logros")
    private String title;

    @Schema(description = "Subtítulo de la sección", example = "Confían en nosotros millones de usuarios")
    private String subtitle;

    @NotNull(message = "El estado activo es requerido")
    @Schema(description = "Si la sección está activa", example = "true")
    private Boolean isActive;
}
