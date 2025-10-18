package com.elsalvador.coopac.controller.admin;

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
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof OidcUser oidcUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Tipo de autenticación no válido");
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
    public ResponseEntity<Map<String, String>> logout() {
        log.info("Cerrando sesión...");
        return ResponseEntity.ok(Map.of(
                "message", "Sesión cerrada correctamente",
                "redirectUrl", "http://localhost:4200/login"
        ));
    }
}
