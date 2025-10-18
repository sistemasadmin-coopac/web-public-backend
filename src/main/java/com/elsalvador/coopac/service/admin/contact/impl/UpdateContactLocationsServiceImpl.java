package com.elsalvador.coopac.service.admin.contact.impl;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.entity.contact.ContactLocations;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.contact.ContactLocationsRepository;
import com.elsalvador.coopac.service.admin.contact.UpdateContactLocationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.CONTACT_PAGE_CACHE;

import java.util.List;
import java.util.UUID;

/**
 * Implementación del servicio para actualizar ubicaciones de contacto
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateContactLocationsServiceImpl implements UpdateContactLocationsService {

    private final ContactLocationsRepository contactLocationsRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    @Override
    @CacheEvict(value = CONTACT_PAGE_CACHE, allEntries = true)
    public ContactAdminDTO.ContactLocationPlaceDTO updateContactLocation(UUID id, ContactAdminDTO.UpdateContactLocationDTO updateDTO) {
        log.info("Actualizando ubicación de contacto con ID: {}", id);

        ContactLocations location = contactLocationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ubicación de contacto no encontrada con ID: " + id));

        if (updateDTO.name() != null) {
            location.setName(updateDTO.name());
        }
        if (updateDTO.address() != null) {
            location.setAddress(updateDTO.address());
        }
        if (updateDTO.phoneOverride() != null) {
            location.setPhoneOverride(updateDTO.phoneOverride());
        }
        if (updateDTO.whatsappOverride() != null) {
            location.setWhatsappOverride(updateDTO.whatsappOverride());
        }
        if (updateDTO.scheduleLabel() != null) {
            location.setScheduleLabel(updateDTO.scheduleLabel());
        }
        if (updateDTO.googleMapsEmbedUrl() != null) {
            location.setGoogleMapsEmbedUrl(updateDTO.googleMapsEmbedUrl());
        }
        if (updateDTO.latitude() != null) {
            location.setLatitude(updateDTO.latitude());
        }
        if (updateDTO.longitude() != null) {
            location.setLongitude(updateDTO.longitude());
        }
        if (updateDTO.displayOrder() != null) {
            location.setDisplayOrder(updateDTO.displayOrder());
        }
        if (updateDTO.isActive() != null) {
            location.setIsActive(updateDTO.isActive());
        }

        ContactLocations updatedLocation = contactLocationsRepository.save(location);
        log.info("Ubicación de contacto actualizada exitosamente");

        return mapToDTO(updatedLocation);
    }

    /**
     * Mapea entidad a DTO
     */
    private ContactAdminDTO.ContactLocationPlaceDTO mapToDTO(ContactLocations location) {
        String phone = location.getPhoneOverride() != null ?
                location.getPhoneOverride() :
                getGlobalValue("phone");

        String whatsapp = location.getWhatsappOverride() != null ?
                location.getWhatsappOverride() :
                getGlobalValue("whatsapp");

        ContactAdminDTO.ContactMapDTO map = new ContactAdminDTO.ContactMapDTO(
                location.getLatitude(),
                location.getLongitude(),
                15
        );

        List<ContactAdminDTO.ContactActionDTO> actions = List.of(
                new ContactAdminDTO.ContactActionDTO("Llamar", "phone", phone.replaceAll("[^0-9+]", ""), false, 0),
                new ContactAdminDTO.ContactActionDTO("WhatsApp", "whatsapp", "https://wa.me/" + whatsapp.replaceAll("[^0-9]", ""), true, 1)
        );

        return new ContactAdminDTO.ContactLocationPlaceDTO(
                location.getId(),
                location.getName(),
                location.getAddress(),
                phone,
                whatsapp,
                location.getScheduleLabel(),
                map,
                actions,
                location.getIsActive()
        );
    }

    /**
     * Obtiene el valor global de configuración para un tipo de canal
     */
    private String getGlobalValue(String channelType) {
        return switch (channelType.toLowerCase()) {
            case "phone" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getPhoneMain())
                    .orElse("+503 2555-1234");
            case "whatsapp" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getWhatsappNumber())
                    .orElse("+503 7890-1234");
            case "email" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getEmailMain())
                    .orElse("info@coopac-elsalvador.com");
            case "location" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(settings -> settings.getAddressLine1())
                    .orElse("Av. Los Héroes, San Salvador");
            default -> "";
        };
    }
}
