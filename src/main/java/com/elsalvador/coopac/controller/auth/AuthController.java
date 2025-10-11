package com.elsalvador.coopac.controller.auth;

import com.elsalvador.coopac.dto.auth.GoogleValidateRequest;
import com.elsalvador.coopac.dto.auth.GoogleValidateResponse;
import com.elsalvador.coopac.dto.auth.UserDto;
import com.elsalvador.coopac.service.auth.GoogleTokenVerifierService;
import com.elsalvador.coopac.service.auth.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * Controlador para autenticación con Google OAuth
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleTokenVerifierService googleTokenVerifier;
    private final JwtService jwtService;

    @Value("${app.authorized.admins}")
    private String authorizedAdminsStr;

    /**
     * Valida el token de Google y genera un JWT propio
     */
    @PostMapping("/google/validate")
    public ResponseEntity<GoogleValidateResponse> validateGoogleToken(
            @Valid @RequestBody GoogleValidateRequest request) {

        log.info("Solicitud de validación de token para email: {}", request.getEmail());

        try {
            // 1. Verificar token de Google
            GoogleIdToken.Payload payload = googleTokenVerifier.verifyToken(request.getToken());

            // 2. Extraer email
            String email = googleTokenVerifier.getEmail(payload);
            if (email == null || email.isEmpty()) {
                log.warn("Email no encontrado en el token de Google");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(GoogleValidateResponse.error("Email no encontrado en el token de Google"));
            }

            // 3. Verificar autorización
            List<String> authorizedAdmins = Arrays.asList(authorizedAdminsStr.split(","));
            boolean isAuthorized = authorizedAdmins.stream()
                    .anyMatch(admin -> admin.trim().equalsIgnoreCase(email));

            if (!isAuthorized) {
                log.warn("Usuario no autorizado intentó acceder: {}", email);
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(GoogleValidateResponse.error("Este usuario no tiene permisos de administrador"));
            }

            // 4. Crear UserDto
            UserDto user = UserDto.builder()
                    .id(googleTokenVerifier.getUserId(payload))
                    .email(email)
                    .name(request.getName())
                    .role("admin")
                    .isActive(true)
                    .profilePicture(request.getPicture())
                    .createdAt(Instant.now())
                    .lastLogin(Instant.now())
                    .build();

            // 5. Generar JWT
            String jwtToken = jwtService.generateToken(user);

            // 6. Log de auditoría
            log.info("Login exitoso para usuario: {} en {}", email, Instant.now());

            // 7. Retornar respuesta
            return ResponseEntity.ok(GoogleValidateResponse.success(jwtToken, user));

        } catch (IllegalArgumentException e) {
            log.error("Error de validación: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(GoogleValidateResponse.error("Token de Google inválido o expirado"));

        } catch (Exception e) {
            log.error("Error interno al validar token: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(GoogleValidateResponse.error("Error interno del servidor"));
        }
    }

    /**
     * Endpoint de health check
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth service is running");
    }
}

