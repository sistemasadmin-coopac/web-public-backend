package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinBenefitService;
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
 * Controller para gestionar beneficios de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/benefits")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Join.TAG_NAME,
    description = SwaggerTags.Join.TAG_DESCRIPTION
)
public class JoinBenefitController {

    private final ManageJoinBenefitService managementService;

    /**
     * Obtiene todos los beneficios
     */
    @GetMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_BENEFITS + " Obtener todos los beneficios",
        description = "Obtiene la lista completa de beneficios de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Beneficios obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinBenefitListDTO> getAllBenefits() {
        JoinAdminDTO.JoinBenefitListDTO benefits = managementService.getAllBenefits();
        return ResponseEntity.ok(benefits);
    }

    /**
     * Crea un nuevo beneficio
     */
    @PostMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_BENEFITS + " Crear nuevo beneficio",
        description = "Crea un nuevo beneficio de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Beneficio creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinBenefitDTO> createBenefit(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinBenefitDTO dto) {
        JoinAdminDTO.JoinBenefitDTO created = managementService.createBenefit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtiene un beneficio por ID
     */
    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_BENEFITS + " Obtener beneficio por ID",
        description = "Obtiene los detalles de un beneficio específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Beneficio obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Beneficio no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinBenefitDTO> getBenefitById(
            @PathVariable
            @Parameter(description = "ID del beneficio")
            UUID id) {
        JoinAdminDTO.JoinBenefitDTO benefit = managementService.getBenefitById(id);
        return ResponseEntity.ok(benefit);
    }

    /**
     * Actualiza un beneficio existente
     */
    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_BENEFITS + " Actualizar beneficio",
        description = "Actualiza los datos de un beneficio existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Beneficio actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Beneficio no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinBenefitDTO> updateBenefit(
            @PathVariable
            @Parameter(description = "ID del beneficio a actualizar")
            UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinBenefitDTO dto) {
        JoinAdminDTO.JoinBenefitDTO updated = managementService.updateBenefit(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un beneficio existente
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_BENEFITS + " Eliminar beneficio",
        description = "Elimina un beneficio de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Beneficio eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Beneficio no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteBenefit(
            @PathVariable
            @Parameter(description = "ID del beneficio a eliminar")
            UUID id) {
        managementService.deleteBenefit(id);
        return ResponseEntity.noContent().build();
    }
}

