package com.elsalvador.coopac.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de ejemplo para endpoints protegidos del dashboard
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    /**
     * Endpoint de ejemplo protegido que requiere autenticación
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getDashboardData(Authentication authentication) {
        String userEmail = authentication.getName();
        log.info("Usuario {} accedió al dashboard en {}", userEmail, Instant.now());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Dashboard data");
        response.put("user", userEmail);
        response.put("timestamp", Instant.now());

        Map<String, Object> data = new HashMap<>();
        data.put("totalUsers", 1250);
        data.put("totalProducts", 45);
        data.put("activeLoans", 380);
        data.put("pendingRequests", 12);

        response.put("data", data);

        return ResponseEntity.ok(response);
    }
}

