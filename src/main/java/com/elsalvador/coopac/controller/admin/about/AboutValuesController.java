package com.elsalvador.coopac.controller.admin.about;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.about.ManageAboutValuesService;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/api/admin/about/values")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.About.TAG_NAME,
    description = SwaggerTags.About.TAG_DESCRIPTION
)
public class AboutValuesController {

    private final ManageAboutValuesService manageAboutValuesService;

    @PostMapping
    @Operation(
        summary = SwaggerTags.About.EMOJI_VALUES + " Crear nuevo valor",
        description = "Crea un nuevo valor organizacional"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Valor creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutValueDTO.class)
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
            description = "No autorizado",
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
    public ResponseEntity<AboutAdminDTO.AboutValueDTO> createValue(
            @Valid @RequestBody AboutAdminDTO.AboutValueDTO dto) {
        AboutAdminDTO.AboutValueDTO created = manageAboutValuesService.createValue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.About.EMOJI_VALUES + " Actualizar valor",
        description = "Actualiza un valor organizacional existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Valor actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AboutAdminDTO.AboutValueDTO.class)
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
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Valor no encontrado",
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
    public ResponseEntity<AboutAdminDTO.AboutValueDTO> updateValue(
            @PathVariable UUID id,
            @Valid @RequestBody AboutAdminDTO.AboutValueDTO dto) {
        AboutAdminDTO.AboutValueDTO updated = manageAboutValuesService.updateValue(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.About.EMOJI_VALUES + " Eliminar valor",
        description = "Elimina un valor organizacional"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Valor eliminado exitosamente"),
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
            description = "No autorizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Valor no encontrado",
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
    public ResponseEntity<Void> deleteValue(@PathVariable UUID id) {
        manageAboutValuesService.deleteValue(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/section")
    @Operation(
        summary = SwaggerTags.About.EMOJI_VALUES + " Actualizar sección de valores",
        description = "Actualiza la configuración de la sección de valores"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Configuración actualizada"),
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
            description = "No autorizado",
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
    public ResponseEntity<AboutAdminDTO.AboutValuesSectionDTO> updateValuesSection(
            @Valid @RequestBody AboutAdminDTO.AboutValuesSectionDTO dto) {
        AboutAdminDTO.AboutValuesSectionDTO updated = manageAboutValuesService.updateValuesSection(dto);
        return ResponseEntity.ok(updated);
    }
}

