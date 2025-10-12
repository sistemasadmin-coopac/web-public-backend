package com.elsalvador.coopac.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir la solicitud de validación de token de Google
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleValidateRequest {

    @NotBlank(message = "Token es requerido")
    private String token;

    @NotBlank(message = "Email es requerido")
    @Email(message = "Email debe ser válido")
    private String email;

    @NotBlank(message = "Nombre es requerido")
    private String name;

    private String picture;
}

