package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Controller para gestionar miembros de junta directiva
 */
@RestController
@RequestMapping("/api/admin/about/board")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.About.TAG_NAME,
    description = SwaggerTags.About.TAG_DESCRIPTION
)
public class AboutBoardController {

    private final ManageAboutBoardService manageAboutBoardService;

    @PostMapping(value = "/members", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = SwaggerTags.About.EMOJI_BOARD + " Crear miembro de junta directiva",
        description = "Crea un nuevo miembro de junta directiva con sus datos personales y foto opcional"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Miembro creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutBoardMemberDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado (requiere rol ADMIN)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutBoardMemberDTO> createBoardMember(
            @Valid @ModelAttribute AboutAdminDTO.AboutBoardMemberDTO dto,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        AboutAdminDTO.AboutBoardMemberDTO created = manageAboutBoardService.createBoardMember(dto, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/members/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = SwaggerTags.About.EMOJI_BOARD + " Actualizar miembro de junta directiva",
        description = "Actualiza los datos de un miembro de junta directiva existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Miembro actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutBoardMemberDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado (requiere rol ADMIN)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Miembro no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutBoardMemberDTO> updateBoardMember(
            @PathVariable UUID id,
            @Valid @ModelAttribute AboutAdminDTO.AboutBoardMemberDTO dto,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        AboutAdminDTO.AboutBoardMemberDTO updated = manageAboutBoardService.updateBoardMember(id, dto, photo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/members/{id}")
    @Operation(
        summary = SwaggerTags.About.EMOJI_BOARD + " Eliminar miembro de junta directiva",
        description = "Elimina un miembro de junta directiva de la base de datos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Miembro eliminado exitosamente"),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado (requiere rol ADMIN)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Miembro no encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<Void> deleteBoardMember(@PathVariable UUID id) {
        manageAboutBoardService.deleteBoardMember(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/section")
    @Operation(
        summary = SwaggerTags.About.EMOJI_BOARD + " Actualizar configuración de la sección",
        description = "Actualiza el título, subtítulo y estado activo de la sección de junta directiva"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuración actualizada exitosamente"),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "No autenticado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "403",
            description = "No autorizado (requiere rol ADMIN)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<AboutAdminDTO.AboutBoardSectionDTO> updateBoardSection(
            @Valid @RequestBody AboutAdminDTO.AboutBoardSectionDTO dto) {
        AboutAdminDTO.AboutBoardSectionDTO updated = manageAboutBoardService.updateBoardSection(dto);
        return ResponseEntity.ok(updated);
    }
}

