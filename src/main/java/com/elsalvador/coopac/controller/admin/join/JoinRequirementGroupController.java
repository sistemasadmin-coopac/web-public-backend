package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinRequirementGroupService;
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
 * Controller para gestionar grupos de requisitos de Join/Asóciate Ya
 */
@RestController
@RequestMapping("/api/admin/join/requirement-groups")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Join.TAG_NAME,
    description = SwaggerTags.Join.TAG_DESCRIPTION
)
public class JoinRequirementGroupController {

    private final ManageJoinRequirementGroupService managementService;

    /**
     * Obtiene todos los grupos de requisitos
     */
    @GetMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_REQUIREMENTS + " Obtener todos los grupos de requisitos",
        description = "Obtiene la lista completa de grupos de requisitos de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grupos obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupListDTO> getAllGroups() {
        JoinAdminDTO.JoinRequirementGroupListDTO groups = managementService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    /**
     * Crea un nuevo grupo de requisitos
     */
    @PostMapping
    @Operation(
        summary = SwaggerTags.Join.EMOJI_REQUIREMENTS + " Crear nuevo grupo de requisitos",
        description = "Crea un nuevo grupo de requisitos para la membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Grupo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupDTO> createGroup(
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto) {
        JoinAdminDTO.JoinRequirementGroupDTO created = managementService.createGroup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Obtiene un grupo de requisitos por ID
     */
    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_REQUIREMENTS + " Obtener grupo de requisitos por ID",
        description = "Obtiene los detalles de un grupo de requisitos específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grupo obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupDTO> getGroupById(
            @PathVariable
            @Parameter(description = "ID del grupo de requisitos")
            UUID id) {
        JoinAdminDTO.JoinRequirementGroupDTO group = managementService.getGroupById(id);
        return ResponseEntity.ok(group);
    }

    /**
     * Actualiza un grupo de requisitos existente
     */
    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_REQUIREMENTS + " Actualizar grupo de requisitos",
        description = "Actualiza los datos de un grupo de requisitos existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Grupo actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<JoinAdminDTO.JoinRequirementGroupDTO> updateGroup(
            @PathVariable
            @Parameter(description = "ID del grupo a actualizar")
            UUID id,
            @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinRequirementGroupDTO dto) {
        JoinAdminDTO.JoinRequirementGroupDTO updated = managementService.updateGroup(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un grupo de requisitos existente
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Join.EMOJI_REQUIREMENTS + " Eliminar grupo de requisitos",
        description = "Elimina un grupo de requisitos de membresía"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Grupo eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<Void> deleteGroup(
            @PathVariable
            @Parameter(description = "ID del grupo a eliminar")
            UUID id) {
        managementService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}

