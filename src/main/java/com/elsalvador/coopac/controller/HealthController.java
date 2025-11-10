package com.elsalvador.coopac.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador para verificación de salud y estado del backend.
 * Proporciona endpoints básicos para monitoreo y redirección de login.
 */
@RestController
@Slf4j
@Tag(name = "🔍 Health Check", description = "Endpoints de verificación de salud y estado del backend")
public class HealthController {

    /**
     * Endpoint raíz que verifica que el backend está funcionando.
     * Retorna información de estado y URL de login.
     *
     * @return Map con estado, mensaje y URL de login
     */
    @GetMapping("/")
    @Operation(
        summary = "🔍 Verificar estado del backend",
        description = "Verifica que el backend está funcionando correctamente y retorna la URL para iniciar sesión"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Backend funcionando correctamente"),
        @ApiResponse(responseCode = "503", description = "Backend no disponible")
    })
    public Map<String, String> home() {
        log.info("✅ Endpoint raíz accedido");
        return Map.of(
                "status", "UP",
                "message", "Backend funcionando correctamente",
                "loginUrl", "/oauth2/authorization/google"
        );
    }

    /**
     * Endpoint de health check simple.
     * Utilizado por load balancers y sistemas de monitoreo.
     *
     * @return Map con estado UP
     */
    @GetMapping("/health")
    @Operation(
        summary = "🔍 Health check",
        description = "Verificación simple de salud del backend. Usado por load balancers y sistemas de monitoreo"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Backend está disponible"),
        @ApiResponse(responseCode = "503", description = "Backend no disponible")
    })
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    /**
     * Obtiene la URL para iniciar sesión con Google OAuth2.
     * Proporciona la URL completa del endpoint de autorización.
     *
     * @return Map con URL de login y descripción
     */
    @GetMapping("/api/login-url")
    @Operation(
        summary = "🔍 Obtener URL de login",
        description = "Retorna la URL completa para iniciar sesión con Google OAuth2"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "URL de login obtenida exitosamente"),
        @ApiResponse(responseCode = "503", description = "Backend no disponible")
    })
    public Map<String, String> getLoginUrl() {
        return Map.of(
                "loginUrl", "http://localhost:8080/oauth2/authorization/google",
                "message", "Redirige a esta URL para iniciar sesión con Google"
        );
    }
}
