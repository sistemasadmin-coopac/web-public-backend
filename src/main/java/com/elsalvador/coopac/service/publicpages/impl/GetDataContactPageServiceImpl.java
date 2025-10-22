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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
     * Calcula automáticamente si está abierto o cerrado según la hora de Perú
     */
    private ContactPageDTO.ScheduleDTO buildScheduleSection() {
        var scheduleEntries = contactScheduleEntriesRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (scheduleEntries.isEmpty()) {
            log.warn("No se encontraron entradas de horario activas");
        }

        // Obtener hora actual en zona horaria de Perú
        ZonedDateTime peruTime = ZonedDateTime.now(ZoneId.of("America/Lima"));
        DayOfWeek todayDayOfWeek = peruTime.getDayOfWeek();
        LocalTime currentTime = peruTime.toLocalTime();

        log.debug("Hora actual en Perú: {} - Día: {}", currentTime, todayDayOfWeek);

        // Procesar cada entrada y actualizar el note con el estado
        scheduleEntries.forEach(entry -> {
            String status = calculateOpenStatus(entry, todayDayOfWeek, currentTime);
            entry.setNote(status);
        });

        // Obtener la nota/estado de la entrada actual (hoy)
        String todayStatus = scheduleEntries.stream()
                .filter(entry -> isDayMatchingEntry(entry, todayDayOfWeek))
                .findFirst()
                .map(ContactScheduleEntries::getNote)
                .orElse(null);

        return contactMapper.mapSchedule(scheduleEntries, "Horarios de Atención", todayStatus);
    }

    /**
     * Calcula si el negocio está abierto o cerrado para una entrada de horario específica
     * @param entry La entrada de horario
     * @param dayOfWeek El día de la semana actual
     * @param currentTime La hora actual
     * @return Texto con el estado (Abierto/Cerrado) y detalles
     */
    private String calculateOpenStatus(ContactScheduleEntries entry, DayOfWeek dayOfWeek, LocalTime currentTime) {
        // Si está marcado como cerrado, mostrar "Cerrado"
        if (entry.getIsClosed()) {
            return "Cerrado";
        }

        // Si la entrada es para hoy
        if (isDayMatchingEntry(entry, dayOfWeek)) {
            if (entry.getOpenTime() != null && entry.getCloseTime() != null) {
                if (currentTime.isAfter(entry.getOpenTime()) && currentTime.isBefore(entry.getCloseTime())) {
                    return String.format("Abierto (hasta las %s)", formatTime(entry.getCloseTime()));
                } else if (currentTime.isBefore(entry.getOpenTime())) {
                    return String.format("Cerrado (abre a las %s)", formatTime(entry.getOpenTime()));
                } else {
                    return String.format("Cerrado (abierto mañana a las %s)", formatTime(entry.getOpenTime()));
                }
            }
        }

        // Para otros días, solo mostrar horario
        if (entry.getOpenTime() != null && entry.getCloseTime() != null) {
            return String.format("De %s a %s", formatTime(entry.getOpenTime()), formatTime(entry.getCloseTime()));
        }

        return "Información no disponible";
    }

    /**
     * Verifica si el día de la semana coincide con la entrada
     * Asume que existen 3 registros: Lunes-Viernes, Sábado, Domingo
     */
    private boolean isDayMatchingEntry(ContactScheduleEntries entry, DayOfWeek dayOfWeek) {
        String label = entry.getLabel().toLowerCase();

        // Lunes a Viernes
        if (label.contains("lunes") || label.contains("viernes") || label.contains("semana")) {
            return dayOfWeek.getValue() >= 1 && dayOfWeek.getValue() <= 5;
        }

        // Sábado
        if (label.contains("sábado") || label.contains("sabado")) {
            return dayOfWeek == DayOfWeek.SATURDAY;
        }

        // Domingo
        if (label.contains("domingo")) {
            return dayOfWeek == DayOfWeek.SUNDAY;
        }

        return false;
    }

    /**
     * Formatea una hora a formato HH:mm
     */
    private String formatTime(LocalTime time) {
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
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
