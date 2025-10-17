package com.elsalvador.coopac.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de ejemplo para endpoints del dashboard
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    /**
     * Endpoint de ejemplo del dashboard
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        log.info("Acceso al dashboard en {}", Instant.now());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Dashboard data");
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
