package com.elsalvador.coopac.controller.admin;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/admin/dashboard")
@Tag(name = SwaggerTags.Dashboard.TAG_NAME, description = SwaggerTags.Dashboard.TAG_DESCRIPTION)
public class DashboardController {

    @GetMapping
    @Operation(
        summary = SwaggerTags.Dashboard.EMOJI_STATS + " Obtener datos del dashboard",
        description = "Obtiene estadisticas y metricas del panel de administracion"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos del dashboard obtenidos exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
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


