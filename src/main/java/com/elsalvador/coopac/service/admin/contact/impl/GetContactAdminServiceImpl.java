package com.elsalvador.coopac.service.admin.contact.impl;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.entity.config.SiteSettings;
import com.elsalvador.coopac.entity.contact.ContactChannels;
import com.elsalvador.coopac.entity.contact.ContactLocations;
import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import com.elsalvador.coopac.entity.page.PageHeaders;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.contact.ContactChannelsRepository;
import com.elsalvador.coopac.repository.contact.ContactLocationsRepository;
import com.elsalvador.coopac.repository.contact.ContactScheduleEntriesRepository;
import com.elsalvador.coopac.service.admin.contact.GetContactAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para obtener datos completos de contacto
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetContactAdminServiceImpl implements GetContactAdminService {

    private final PageHeadersRepository pageHeadersRepository;
    private final ContactChannelsRepository contactChannelsRepository;
    private final ContactScheduleEntriesRepository contactScheduleRepository;
    private final ContactLocationsRepository contactLocationsRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    @Override
    public ContactAdminDTO.ContactPageResponseDTO getContactCompleteData() {
        log.info("Obteniendo datos completos de contacto para administración");

        ContactAdminDTO.ContactHeaderDTO header = buildHeader();
        ContactAdminDTO.ContactSectionsDTO sections = buildSections();

        return new ContactAdminDTO.ContactPageResponseDTO(header, sections);
    }

    /**
     * Construye el header de contacto
     */
    private ContactAdminDTO.ContactHeaderDTO buildHeader() {
        PageHeaders pageHeader = pageHeadersRepository
                .findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(PageSlug.CONTACT.getValue())
                .orElse(null);

        if (pageHeader == null) {
            log.warn("No se encontró header activo para la página de contacto");
            return new ContactAdminDTO.ContactHeaderDTO("Contacto", "Estamos aquí para ayudarte");
        }

        return new ContactAdminDTO.ContactHeaderDTO(
                pageHeader.getTitleMain(),
                pageHeader.getSubtitle()
        );
    }

    /**
     * Construye todas las secciones de contacto
     */
    private ContactAdminDTO.ContactSectionsDTO buildSections() {
        ContactAdminDTO.ContactInfoSectionDTO contactInfo = buildContactInfoSection();
        ContactAdminDTO.ContactScheduleSectionDTO schedule = buildScheduleSection();
        ContactAdminDTO.ContactLocationSectionDTO location = buildLocationSection();

        return new ContactAdminDTO.ContactSectionsDTO(contactInfo, schedule, location);
    }

    /**
     * Construye la sección de información de contacto
     */
    private ContactAdminDTO.ContactInfoSectionDTO buildContactInfoSection() {
        List<ContactChannels> channels = contactChannelsRepository.findAllByOrderByDisplayOrderAsc();

        List<ContactAdminDTO.ContactChannelItemDTO> items = channels.stream()
                .map(this::mapContactChannel)
                .collect(Collectors.toList());

        return new ContactAdminDTO.ContactInfoSectionDTO("Información de Contacto", items);
    }

    /**
     * Construye la sección de horarios
     */
    private ContactAdminDTO.ContactScheduleSectionDTO buildScheduleSection() {
        List<ContactScheduleEntries> scheduleEntries = contactScheduleRepository.findAllByOrderByDisplayOrderAsc();

        List<ContactAdminDTO.ContactScheduleItemDTO> items = scheduleEntries.stream()
                .map(this::mapScheduleEntry)
                .collect(Collectors.toList());

        return new ContactAdminDTO.ContactScheduleSectionDTO(
                "Horarios de Atención",
                items,
                "En días festivos nuestras oficinas permanecen cerradas."
        );
    }

    /**
     * Construye la sección de ubicaciones
     */
    private ContactAdminDTO.ContactLocationSectionDTO buildLocationSection() {
        List<ContactLocations> locations = contactLocationsRepository.findAllByOrderByDisplayOrderAsc();

        List<ContactAdminDTO.ContactLocationPlaceDTO> places = locations.stream()
                .map(this::mapLocationPlace)
                .collect(Collectors.toList());

        return new ContactAdminDTO.ContactLocationSectionDTO(
                "Nuestras Ubicaciones",
                "Visítanos en nuestras oficinas",
                places
        );
    }

    /**
     * Mapea un canal de contacto
     */
    private ContactAdminDTO.ContactChannelItemDTO mapContactChannel(ContactChannels channel) {
        String value = channel.getUseGlobalValue() ?
                getGlobalValue(channel.getChannelType()) :
                channel.getCustomValue();

        return new ContactAdminDTO.ContactChannelItemDTO(
                channel.getId(),
                channel.getChannelType(),
                channel.getIcon(),
                channel.getLabel(),
                value,
                channel.getDescription(),
                channel.getDisplayOrder(),
                channel.getUseGlobalValue(),
                channel.getCustomValue(),
                channel.getIsActive()
        );
    }

    /**
     * Mapea una entrada de horario
     */
    private ContactAdminDTO.ContactScheduleItemDTO mapScheduleEntry(ContactScheduleEntries entry) {
        return new ContactAdminDTO.ContactScheduleItemDTO(
                entry.getId(),
                entry.getLabel(),
                entry.getOpenTime() != null ? entry.getOpenTime().toString() : "",
                entry.getCloseTime() != null ? entry.getCloseTime().toString() : "",
                entry.getIsClosed(),
                entry.getDisplayOrder(),
                entry.getNote(),
                entry.getIsActive()
        );
    }

    /**
     * Mapea una ubicación
     */
    private ContactAdminDTO.ContactLocationPlaceDTO mapLocationPlace(ContactLocations location) {
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
                    .map(SiteSettings::getPhoneMain)
                    .orElse("");
            case "whatsapp" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(SiteSettings::getWhatsappNumber)
                    .orElse("");
            case "email" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(SiteSettings::getEmailMain)
                    .orElse("");
            case "location" -> siteSettingsRepository.findFirstByOrderByUpdatedAtDesc()
                    .map(SiteSettings::getAddressLine1)
                    .orElse("");
            default -> "";
        };
    }
}

