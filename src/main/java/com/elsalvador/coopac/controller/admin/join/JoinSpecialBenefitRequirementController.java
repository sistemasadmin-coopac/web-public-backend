package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinSpecialBenefitRequirementService;
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
 * Controller para gestionar requisitos de beneficios especiales de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/special-benefits/requirements")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Join.TAG_NAME,
    description = SwaggerTags.Join.TAG_DESCRIPTION
)
public class JoinSpecialBenefitRequirementController {

    private final ManageJoinSpecialBenefitRequirementService managementService;

    /**
     * Obtiene un requisito de beneficio especial por ID
     */
    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Obtener requisito por ID",
        description = "Obtiene los detalles de un requisito de beneficio especial específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Requisito obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Requisito no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> getRequirementById(
            @PathVariable
            @Parameter(description = "ID del requisito")
            UUID id) {
        JoinAdminDTO.JoinSpecialBenefitRequirementDTO requirement = managementService.getRequirementById(id);
        return ResponseEntity.ok(requirement);
    }

    /**
     * Obtiene todos los requisitos de beneficios especiales
     */
    @GetMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Obtener todos los requisitos",
        description = "Obtiene la lista completa de requisitos de beneficios especiales"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Requisitos obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementListDTO> getAllRequirements() {
        JoinAdminDTO.JoinSpecialBenefitRequirementListDTO requirements = managementService.getAllRequirements();
        return ResponseEntity.ok(requirements);
    }

    /**
     * Obtiene todos los requisitos de un beneficio especial
     */
    @GetMapping("/benefit/{joinSpecialBenefitId}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Obtener requisitos por beneficio",
        description = "Obtiene todos los requisitos asociados a un beneficio especial específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Requisitos obtenidos exitosamente"),
        @ApiResponse(responseCode = "404", description = "Beneficio no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementListDTO> getRequirementsByBenefit(
            @PathVariable
            @Parameter(description = "ID del beneficio especial")
            UUID joinSpecialBenefitId) {
        JoinAdminDTO.JoinSpecialBenefitRequirementListDTO requirements = managementService.getRequirementsByBenefit(joinSpecialBenefitId);
        return ResponseEntity.ok(requirements);
    }

    /**
     * Crea un nuevo requisito para un beneficio especial
     */
    @PostMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Crear nuevo requisito",
        description = "Crea un nuevo requisito para un beneficio especial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Requisito creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> createRequirement(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto) {
        JoinAdminDTO.JoinSpecialBenefitRequirementDTO created = managementService.createRequirement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Actualiza un requisito de beneficio especial existente
     */
    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Actualizar requisito",
        description = "Actualiza los datos de un requisito de beneficio especial existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Requisito actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Requisito no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitRequirementDTO> updateRequirement(
            @PathVariable
            @Parameter(description = "ID del requisito a actualizar")
            UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitRequirementDTO dto) {
        JoinAdminDTO.JoinSpecialBenefitRequirementDTO updated = managementService.updateRequirement(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un requisito de beneficio especial
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Eliminar requisito",
        description = "Elimina un requisito de un beneficio especial"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Requisito eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Requisito no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteRequirement(
            @PathVariable
            @Parameter(description = "ID del requisito a eliminar")
            UUID id) {
        managementService.deleteRequirement(id);
        return ResponseEntity.noContent().build();
    }
}

