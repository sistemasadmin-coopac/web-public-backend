package com.elsalvador.coopac.controller.admin.contact;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.admin.contact.GetContactAdminService;
import com.elsalvador.coopac.service.admin.contact.UpdateContactChannelsService;
import com.elsalvador.coopac.service.admin.contact.UpdateContactLocationsService;
import com.elsalvador.coopac.service.admin.contact.UpdateContactScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller para administración de contacto
 */
@RestController
@RequestMapping("/api/admin/contact")
@RequiredArgsConstructor
@Tag(
    name = SwaggerTags.Contact.TAG_NAME,
    description = SwaggerTags.Contact.TAG_DESCRIPTION
)
public class ContactAdminController {

    private final GetContactAdminService getContactAdminService;
    private final UpdateContactChannelsService updateContactChannelsService;
    private final UpdateContactScheduleService updateContactScheduleService;
    private final UpdateContactLocationsService updateContactLocationsService;

    /**
     * Obtiene todos los datos de contacto completos
     */
    @GetMapping("/complete")
    @Operation(
        summary = SwaggerTags.Contact.EMOJI_GENERAL + " Obtener todos los datos de contacto",
        description = "Retorna todos los datos de contacto incluyendo canales, horarios y ubicaciones"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Datos obtenidos exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ContactAdminDTO.ContactPageResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ContactAdminDTO.ContactPageResponseDTO> getContactCompleteData() {
        ContactAdminDTO.ContactPageResponseDTO contactData = getContactAdminService.getContactCompleteData();
        return ResponseEntity.ok(contactData);
    }

    /**
     * Actualiza un canal de contacto
     */
    @PutMapping("/channels/{id}")
    @Operation(
        summary = SwaggerTags.Contact.EMOJI_CHANNELS + " Actualizar canal de contacto",
        description = "Actualiza los datos de un canal de contacto (email, teléfono, WhatsApp, etc.)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Canal actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ContactAdminDTO.ContactChannelItemDTO.class)
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
            description = "Canal no encontrado",
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
    public ResponseEntity<ContactAdminDTO.ContactChannelItemDTO> updateContactChannel(
            @PathVariable
            @Parameter(description = "ID único del canal a actualizar")
            UUID id,
            @Valid @RequestBody
            @Schema(description = "Datos actualizados del canal")
            ContactAdminDTO.UpdateContactChannelDTO updateDTO) {
        ContactAdminDTO.ContactChannelItemDTO channel =
                updateContactChannelsService.updateContactChannel(id, updateDTO);
        return ResponseEntity.ok(channel);
    }

    /**
     * Actualiza un horario de contacto
     */
    @PutMapping("/schedule/{id}")
    @Operation(
        summary = SwaggerTags.Contact.EMOJI_SCHEDULE + " Actualizar horario de contacto",
        description = "Actualiza los horarios de atención de un día o período específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Horario actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ContactAdminDTO.ContactScheduleItemDTO.class)
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
            description = "Horario no encontrado",
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
    public ResponseEntity<ContactAdminDTO.ContactScheduleItemDTO> updateContactSchedule(
            @PathVariable
            @Parameter(description = "ID único del horario a actualizar")
            UUID id,
            @Valid @RequestBody
            @Schema(description = "Datos actualizados del horario (hora de apertura y cierre)")
            ContactAdminDTO.UpdateContactScheduleDTO updateDTO) {
        ContactAdminDTO.ContactScheduleItemDTO schedule =
                updateContactScheduleService.updateContactSchedule(id, updateDTO);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Actualiza una ubicación de contacto
     */
    @PutMapping("/locations/{id}")
    @Operation(
        summary = SwaggerTags.Contact.EMOJI_LOCATIONS + " Actualizar ubicación de contacto",
        description = "Actualiza los datos de una ubicación incluyendo dirección, teléfono, coordenadas y mapa"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ubicación actualizada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ContactAdminDTO.ContactLocationPlaceDTO.class)
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
            description = "Ubicación no encontrada",
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
    public ResponseEntity<ContactAdminDTO.ContactLocationPlaceDTO> updateContactLocation(
            @PathVariable
            @Parameter(description = "ID único de la ubicación a actualizar")
            UUID id,
            @Valid @RequestBody
            @Schema(description = "Datos actualizados de la ubicación")
            ContactAdminDTO.UpdateContactLocationDTO updateDTO) {
        ContactAdminDTO.ContactLocationPlaceDTO location =
                updateContactLocationsService.updateContactLocation(id, updateDTO);
        return ResponseEntity.ok(location);
    }
}
