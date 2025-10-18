package com.elsalvador.coopac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class HealthController {

    @GetMapping("/")
    public Map<String, String> home() {
        log.info("✅ Endpoint raíz accedido");
        return Map.of(
                "status", "UP",
                "message", "Backend funcionando correctamente",
                "loginUrl", "/oauth2/authorization/google"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping("/api/login-url")
    public Map<String, String> getLoginUrl() {
        return Map.of(
                "loginUrl", "http://localhost:8080/oauth2/authorization/google",
                "message", "Redirige a esta URL para iniciar sesión con Google"
        );
    }
}
