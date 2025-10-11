package com.elsalvador.coopac.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para responder con el resultado de la validación
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleValidateResponse {

    private Boolean success;
    private String token;
    private UserDto user;
    private String error;

    public static GoogleValidateResponse success(String token, UserDto user) {
        return GoogleValidateResponse.builder()
                .success(true)
                .token(token)
                .user(user)
                .build();
    }

    public static GoogleValidateResponse error(String errorMessage) {
        return GoogleValidateResponse.builder()
                .success(false)
                .error(errorMessage)
                .build();
    }
}
