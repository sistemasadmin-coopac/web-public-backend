package com.elsalvador.coopac.controller.admin.home;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.home.GetHomeStatsService;
import com.elsalvador.coopac.service.admin.home.ManageHomeStatsService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/home-stats")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Home.TAG_NAME,
    description = SwaggerTags.Home.TAG_DESCRIPTION
)
public class HomeStatsController {

    private final GetHomeStatsService getService;
    private final ManageHomeStatsService manageService;

    @GetMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Obtener todas las estadísticas",
        description = "Obtiene la lista de estadísticas activas del home"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente"),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<HomeStatsDTO>> getAllActiveStats() {
        List<HomeStatsDTO> stats = getService.getAllActiveStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Obtener estadística por ID",
        description = "Obtiene los detalles de una estadística específica"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadística obtenida exitosamente"),
        @ApiResponse(
            responseCode = "404",
            description = "Estadística no encontrada",
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
    public ResponseEntity<HomeStatsDTO> getStatById(
            @PathVariable
            @Parameter(description = "ID de la estadística")
            UUID id) {
        HomeStatsDTO stat = getService.getStatById(id);
        return ResponseEntity.ok(stat);
    }

    @PostMapping
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Crear nueva estadística",
        description = "Crea una nueva estadística para el home"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Estadística creada exitosamente",
            content = @Content(mediaType = "application/json")
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
    public ResponseEntity<HomeStatsDTO> createStat(
            @Valid @RequestBody HomeStatsDTO statDTO) {
        HomeStatsDTO createdStat = manageService.createStat(statDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStat);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Actualizar estadística",
        description = "Actualiza una estadística existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadística actualizada exitosamente"),
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
            description = "Estadística no encontrada",
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
    public ResponseEntity<HomeStatsDTO> updateStat(
            @PathVariable
            @Parameter(description = "ID de la estadística a actualizar")
            UUID id,
            @Valid @RequestBody HomeStatsDTO statDTO) {
        HomeStatsDTO updatedStat = manageService.updateStat(id, statDTO);
        return ResponseEntity.ok(updatedStat);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = SwaggerTags.Home.EMOJI_STATS + " Eliminar estadística",
        description = "Elimina una estadística del home"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estadística eliminada exitosamente"),
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
            description = "Estadística no encontrada",
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
    public ResponseEntity<Void> deleteStat(
            @PathVariable
            @Parameter(description = "ID de la estadística a eliminar")
            UUID id) {
        manageService.deleteStat(id);
        return ResponseEntity.noContent().build();
    }
}
