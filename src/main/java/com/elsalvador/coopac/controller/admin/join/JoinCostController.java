package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinCostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para gestionar costos de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/costs")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Join.TAG_NAME,
    description = SwaggerTags.Join.TAG_DESCRIPTION
)
public class JoinCostController {

    private final ManageJoinCostService managementService;

    /**
     * Obtiene todos los costos
     */
    @GetMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_COSTS + " Obtener todos los costos",
        description = "Obtiene la lista completa de costos de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Costos obtenidos exitosamente"),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))
        )
    })
    public ResponseEntity<JoinAdminDTO.JoinCostListDTO> getAllCosts() {
        JoinAdminDTO.JoinCostListDTO costs = managementService.getAllCosts();
        return ResponseEntity.ok(costs);
    }

    /**
     * Crea un nuevo costo
     */
    @PostMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_COSTS + " Crear nuevo costo",
        description = "Crea un nuevo registro de costo de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Costo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinCostDTO> createCost(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinCostDTO dto) {
        JoinAdminDTO.JoinCostDTO created = managementService.createCost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtiene un costo por ID
     */
    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_COSTS + " Obtener costo por ID",
        description = "Obtiene los detalles de un costo específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Costo obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Costo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinCostDTO> getCostById(
            @PathVariable
            @Parameter(description = "ID del costo")
            UUID id) {
        JoinAdminDTO.JoinCostDTO cost = managementService.getCostById(id);
        return ResponseEntity.ok(cost);
    }

    /**
     * Actualiza un costo existente
     */
    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_COSTS + " Actualizar costo",
        description = "Actualiza los datos de un costo existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Costo actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Costo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinCostDTO> updateCost(
            @PathVariable
            @Parameter(description = "ID del costo a actualizar")
            UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinCostDTO dto) {
        JoinAdminDTO.JoinCostDTO updated = managementService.updateCost(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un costo existente
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_COSTS + " Eliminar costo",
        description = "Elimina un costo de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Costo eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Costo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteCost(
            @PathVariable
            @Parameter(description = "ID del costo a eliminar")
            UUID id) {
        managementService.deleteCost(id);
        return ResponseEntity.noContent().build();
    }
}

