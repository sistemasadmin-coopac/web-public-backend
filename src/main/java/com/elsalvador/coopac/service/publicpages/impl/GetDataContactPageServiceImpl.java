package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.contact.ContactPageDTO;
import com.elsalvador.coopac.entity.config.SiteSettings;
import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.contact.ContactChannelsRepository;
import com.elsalvador.coopac.repository.contact.ContactLocationsRepository;
import com.elsalvador.coopac.repository.contact.ContactScheduleEntriesRepository;
import com.elsalvador.coopac.service.publicpages.GetDataContactPageService;
import com.elsalvador.coopac.service.publicpages.mapper.contact.ContactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.CONTACT_PAGE_CACHE;

/**
 * Servicio para la página Contact que orquesta todos los datos necesarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataContactPageServiceImpl implements GetDataContactPageService {

    // Repositorios
    private final PageHeadersRepository pageHeadersRepository;
    private final ContactChannelsRepository contactChannelsRepository;
    private final ContactScheduleEntriesRepository contactScheduleEntriesRepository;
    private final ContactLocationsRepository contactLocationsRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    // Mapper
    private final ContactMapper contactMapper;

    /**
     * Obtiene todos los datos necesarios para la página Contact
     * Cache se actualiza cada 6 horas
     */
    @Cacheable(value = CONTACT_PAGE_CACHE, key = "'contact'")
    public ContactPageDTO getContactPageData() {
        log.debug("Obteniendo datos de la página Contact desde la base de datos");

        var header = buildHeader();
        var sections = buildSections();

        return new ContactPageDTO(header, sections);
    }

    /**
     * Construye el header de la página Contact usando PageHeadersRepository
     */
    private ContactPageDTO.HeaderDTO buildHeader() {
        var pageHeader = pageHeadersRepository
            .findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(PageSlug.CONTACT.getValue())
            .orElse(null);

        if (pageHeader == null) {
            log.warn("No se encontró header activo para la página Contact");
            return createDefaultHeader();
        }

        return contactMapper.mapHeader(pageHeader);
    }

    /**
     * Crea header por defecto como fallback
     */
    private ContactPageDTO.HeaderDTO createDefaultHeader() {
        return new ContactPageDTO.HeaderDTO(
                "Contáctanos",
                "Estamos aquí para ayudarte con cualquier consulta sobre nuestros servicios financieros."
        );
    }

    /**
     * Construye todas las secciones de la página Contact
     */
    private ContactPageDTO.SectionsDTO buildSections() {
        return new ContactPageDTO.SectionsDTO(
                buildContactInfoSection(),
                buildScheduleSection(),
                buildLocationSection()
        );
    }

    /**
     * Construye la sección de información de contacto
     */
    private ContactPageDTO.ContactInfoDTO buildContactInfoSection() {
        var channels = contactChannelsRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (channels.isEmpty()) {
            log.warn("No se encontraron canales de contacto activos");
        }

        // Procesar valores globales para canales que los usan
        channels.forEach(channel -> {
            if (channel.getUseGlobalValue()) {
                String globalValue = getGlobalValueFromSettings(channel.getChannelType());
                // Simular el valor global asignándolo al customValue para el mapper
                channel.setCustomValue(globalValue);
                channel.setUseGlobalValue(false); // Temporalmente para el mapper
            }
        });

        return contactMapper.mapContactInfo(channels, "Información de Contacto");
    }

    /**
     * Obtiene valores globales de configuración desde site_settings
     */
    private String getGlobalValueFromSettings(String channelType) {
        var siteSettings = siteSettingsRepository.findAll().stream().findFirst().orElse(null);

        if (siteSettings == null) {
            log.warn("No se encontraron configuraciones del sitio");
            return "";
        }

        return switch (channelType.toLowerCase()) {
            case "phone" -> siteSettings.getPhoneMain() != null ? siteSettings.getPhoneMain() : "";
            case "whatsapp" -> siteSettings.getWhatsappNumber() != null ? siteSettings.getWhatsappNumber() : "";
            case "email" -> siteSettings.getEmailMain() != null ? siteSettings.getEmailMain() : "";
            case "location" -> buildFullAddress(siteSettings);
            default -> "";
        };
    }

    /**
     * Construye la dirección completa desde site_settings
     */
    private String buildFullAddress(SiteSettings settings) {
        StringBuilder address = new StringBuilder();

        if (settings.getAddressLine1() != null) {
            address.append(settings.getAddressLine1());
        }
        if (settings.getAddressLine2() != null) {
            if (!address.isEmpty()) address.append(", ");
            address.append(settings.getAddressLine2());
        }
        if (settings.getCity() != null) {
            if (!address.isEmpty()) address.append(", ");
            address.append(settings.getCity());
        }

        return address.toString();
    }

    /**
     * Construye la sección de horarios
     */
    private ContactPageDTO.ScheduleDTO buildScheduleSection() {
        var scheduleEntries = contactScheduleEntriesRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (scheduleEntries.isEmpty()) {
            log.warn("No se encontraron entradas de horario activas");
        }

        // Obtener nota general de la primera entrada que tenga una
        String note = scheduleEntries.stream()
                .filter(entry -> entry.getNote() != null && !entry.getNote().trim().isEmpty())
                .findFirst()
                .map(ContactScheduleEntries::getNote)
                .orElse(null);

        return contactMapper.mapSchedule(scheduleEntries, "Horarios de Atención", note);
    }

    /**
     * Construye la sección de ubicaciones
     */
    private ContactPageDTO.LocationDTO buildLocationSection() {
        var locations = contactLocationsRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (locations.isEmpty()) {
            log.warn("No se encontraron ubicaciones activas");
        }

        // Completar valores globales para ubicaciones que no tienen override
        var siteSettings = siteSettingsRepository.findAll().stream().findFirst().orElse(null);

        locations.forEach(location -> {
            if (location.getPhoneOverride() == null && siteSettings != null) {
                location.setPhoneOverride(siteSettings.getPhoneMain());
            }
            if (location.getWhatsappOverride() == null && siteSettings != null) {
                location.setWhatsappOverride(siteSettings.getWhatsappNumber());
            }
        });

        return contactMapper.mapLocation(locations, "Nuestras Ubicaciones", "Visítanos en nuestras oficinas");
    }
}
