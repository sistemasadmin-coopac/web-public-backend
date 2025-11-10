package com.elsalvador.coopac.controller.admin;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = SwaggerTags.Auth.TAG_NAME, description = SwaggerTags.Auth.TAG_DESCRIPTION)
public class AuthController {

    @GetMapping("/me")
    @Operation(
        summary = SwaggerTags.Auth.EMOJI_PROFILE + " Obtener usuario actual",
        description = "Obtiene informacion del usuario autenticado incluyendo email, nombre y roles"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof OidcUser oidcUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Tipo de autenticacion no valido");
        }

        // Extraer roles (sin el prefijo ROLE_)
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replace("ROLE_", ""))
                .toList();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", oidcUser.getSubject());
        userInfo.put("username", oidcUser.getEmail());
        userInfo.put("email", oidcUser.getEmail());
        userInfo.put("displayName", oidcUser.getFullName());
        userInfo.put("pictureUrl", oidcUser.getPicture());
        userInfo.put("roles", roles);

        log.info("Usuario obtenido: {} con roles: {}", oidcUser.getEmail(), roles);

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/logout")
    @Operation(
        summary = SwaggerTags.Auth.EMOJI_LOGOUT + " Cerrar sesion",
        description = "Cierra la sesion del usuario autenticado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sesion cerrada exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Map<String, String>> logout() {
        log.info("Cerrando sesion...");
        return ResponseEntity.ok(Map.of(
                "message", "Sesion cerrada correctamente",
                "redirectUrl", "http://localhost:4200/login"
        ));
    }
}


