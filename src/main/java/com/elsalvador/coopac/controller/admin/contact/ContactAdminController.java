package com.elsalvador.coopac.controller.admin.contact;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.service.admin.contact.GetContactAdminService;
import com.elsalvador.coopac.service.admin.contact.UpdateContactChannelsService;
import com.elsalvador.coopac.service.admin.contact.UpdateContactLocationsService;
import com.elsalvador.coopac.service.admin.contact.UpdateContactScheduleService;
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
public class ContactAdminController {

    private final GetContactAdminService getContactAdminService;
    private final UpdateContactChannelsService updateContactChannelsService;
    private final UpdateContactScheduleService updateContactScheduleService;
    private final UpdateContactLocationsService updateContactLocationsService;

    /**
     * Obtiene todos los datos de contacto completos
     */
    @GetMapping("/complete")
    public ResponseEntity<ContactAdminDTO.ContactPageResponseDTO> getContactCompleteData() {
        ContactAdminDTO.ContactPageResponseDTO contactData = getContactAdminService.getContactCompleteData();
        return ResponseEntity.ok(contactData);
    }

    /**
     * Actualiza un canal de contacto
     */
    @PutMapping("/channels/{id}")
    public ResponseEntity<ContactAdminDTO.ContactChannelItemDTO> updateContactChannel(
            @PathVariable UUID id,
            @Valid @RequestBody ContactAdminDTO.UpdateContactChannelDTO updateDTO) {
        ContactAdminDTO.ContactChannelItemDTO channel =
                updateContactChannelsService.updateContactChannel(id, updateDTO);
        return ResponseEntity.ok(channel);
    }

    /**
     * Actualiza un horario de contacto
     */
    @PutMapping("/schedule/{id}")
    public ResponseEntity<ContactAdminDTO.ContactScheduleItemDTO> updateContactSchedule(
            @PathVariable UUID id,
            @Valid @RequestBody ContactAdminDTO.UpdateContactScheduleDTO updateDTO) {
        ContactAdminDTO.ContactScheduleItemDTO schedule =
                updateContactScheduleService.updateContactSchedule(id, updateDTO);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Actualiza una ubicación de contacto
     */
    @PutMapping("/locations/{id}")
    public ResponseEntity<ContactAdminDTO.ContactLocationPlaceDTO> updateContactLocation(
            @PathVariable UUID id,
            @Valid @RequestBody ContactAdminDTO.UpdateContactLocationDTO updateDTO) {
        ContactAdminDTO.ContactLocationPlaceDTO location =
                updateContactLocationsService.updateContactLocation(id, updateDTO);
        return ResponseEntity.ok(location);
    }
}
