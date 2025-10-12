package com.elsalvador.coopac.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * DTO para representar los datos del usuario autenticado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String id;
    private String email;
    private String name;
    private String role;
    private Boolean isActive;
    private String profilePicture;
    private Instant createdAt;
    private Instant lastLogin;
}

