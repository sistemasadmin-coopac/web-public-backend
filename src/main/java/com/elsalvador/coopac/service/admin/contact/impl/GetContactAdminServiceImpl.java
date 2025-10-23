package com.elsalvador.coopac.service.admin.contact.impl;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;
import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;
import com.elsalvador.coopac.entity.contact.ContactChannels;
import com.elsalvador.coopac.entity.contact.ContactLocations;
import com.elsalvador.coopac.entity.contact.ContactScheduleEntries;
import com.elsalvador.coopac.entity.home.HomeStats;
import com.elsalvador.coopac.entity.home.HomeStatsSection;
import com.elsalvador.coopac.entity.page.PageHeaders;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeStatsRepository;
import com.elsalvador.coopac.repository.HomeStatsSectionRepository;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.contact.ContactChannelsRepository;
import com.elsalvador.coopac.repository.contact.ContactLocationsRepository;
import com.elsalvador.coopac.repository.contact.ContactScheduleEntriesRepository;
import com.elsalvador.coopac.config.CacheConfig;
import com.elsalvador.coopac.service.admin.contact.GetContactAdminService;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
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

    /**
     * Implementación del servicio para gestionar secciones de estadísticas del home
     */
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class HomeStatsSectionServiceImpl implements GetHomePromotionsService.HomeStatsSectionService {

        private final HomeStatsSectionRepository homeStatsSectionRepository;

        @Override
        @Transactional(readOnly = true)
        public List<HomeStatsSectionDTO> getAllSections() {
            log.debug("Obteniendo todas las secciones de estadísticas del home");
            return homeStatsSectionRepository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public HomeStatsSectionDTO getActiveSection() {
            log.debug("Obteniendo sección activa de estadísticas del home");
            return homeStatsSectionRepository.findFirstByIsActiveTrue()
                    .map(this::convertToDTO)
                    .orElse(null);
        }

        @Override
        @Transactional(readOnly = true)
        public HomeStatsSectionDTO getSectionById(UUID id) {
            log.debug("Obteniendo sección de estadísticas con ID: {}", id);
            HomeStatsSection section = homeStatsSectionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sección de estadísticas no encontrada con ID: " + id));
            return convertToDTO(section);
        }

        @Override
        @Transactional
        @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
        public HomeStatsSectionDTO createSection(HomeStatsSectionDTO dto) {
            log.debug("Creando nueva sección de estadísticas: {}", dto.getTitle());

            HomeStatsSection section = HomeStatsSection.builder()
                    .title(dto.getTitle())
                    .subtitle(dto.getSubtitle())
                    .isActive(dto.getIsActive())
                    .build();

            HomeStatsSection savedSection = homeStatsSectionRepository.save(section);
            log.info("Sección de estadísticas creada con ID: {}", savedSection.getId());

            return convertToDTO(savedSection);
        }

        @Override
        @Transactional
        @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
        public HomeStatsSectionDTO updateSection(UUID id, HomeStatsSectionDTO dto) {
            log.debug("Actualizando sección de estadísticas con ID: {}", id);

            HomeStatsSection existingSection = homeStatsSectionRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sección de estadísticas no encontrada con ID: " + id));

            existingSection.setTitle(dto.getTitle());
            existingSection.setSubtitle(dto.getSubtitle());
            existingSection.setIsActive(dto.getIsActive());

            HomeStatsSection updatedSection = homeStatsSectionRepository.save(existingSection);
            log.info("Sección de estadísticas actualizada con ID: {}", updatedSection.getId());

            return convertToDTO(updatedSection);
        }

        @Override
        @Transactional
        @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
        public void deleteSection(UUID id) {
            log.debug("Eliminando sección de estadísticas con ID: {}", id);

            if (!homeStatsSectionRepository.existsById(id)) {
                throw new ResourceNotFoundException("Sección de estadísticas no encontrada con ID: " + id);
            }

            homeStatsSectionRepository.deleteById(id);
            log.info("Sección de estadísticas eliminada con ID: {}", id);
        }

        /**
         * Convierte entidad a DTO
         */
        private HomeStatsSectionDTO convertToDTO(HomeStatsSection section) {
            return HomeStatsSectionDTO.builder()
                    .id(section.getId())
                    .title(section.getTitle())
                    .subtitle(section.getSubtitle())
                    .isActive(section.getIsActive())
                    .build();
        }
    }

    /**
     * Implementación del servicio para gestionar estadísticas del home
     */
    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class HomeStatsServiceImpl implements GetHomePromotionsService.HomeStatsService {

        private final HomeStatsRepository homeStatsRepository;

        @Override
        @Transactional(readOnly = true)
        public List<HomeStatsDTO> getAllStats() {
            log.debug("Obteniendo todas las estadísticas del home");
            return homeStatsRepository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public List<HomeStatsDTO> getActiveStats() {
            log.debug("Obteniendo estadísticas activas del home");
            return homeStatsRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        @Transactional(readOnly = true)
        public HomeStatsDTO getStatsById(UUID id) {
            log.debug("Obteniendo estadística con ID: {}", id);
            HomeStats stats = homeStatsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));
            return convertToDTO(stats);
        }

        @Override
        @Transactional
        @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
        public HomeStatsDTO createStats(HomeStatsDTO dto) {
            log.debug("Creando nueva estadística: {}", dto.getLabel());

            // Calcular automáticamente el siguiente displayOrder
            Integer displayOrder = homeStatsRepository.findMaxDisplayOrder() + 1;

            HomeStats stats = HomeStats.builder()
                    .label(dto.getLabel())
                    .valueText(dto.getValueText())
                    .icon(dto.getIcon())
                    .displayOrder(displayOrder)
                    .isActive(dto.getIsActive())
                    .build();

            HomeStats savedStats = homeStatsRepository.save(stats);
            log.info("Estadística creada con ID: {}", savedStats.getId());

            return convertToDTO(savedStats);
        }

        @Override
        @Transactional
        @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
        public HomeStatsDTO updateStats(UUID id, HomeStatsDTO dto) {
            log.debug("Actualizando estadística con ID: {}", id);

            HomeStats existingStats = homeStatsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));

            existingStats.setLabel(dto.getLabel());
            existingStats.setValueText(dto.getValueText());
            existingStats.setIcon(dto.getIcon());
            existingStats.setIsActive(dto.getIsActive());

            HomeStats updatedStats = homeStatsRepository.save(existingStats);
            log.info("Estadística actualizada con ID: {}", updatedStats.getId());

            return convertToDTO(updatedStats);
        }

        @Override
        @Transactional
        @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
        public void deleteStats(UUID id) {
            log.debug("Eliminando estadística con ID: {}", id);

            if (!homeStatsRepository.existsById(id)) {
                throw new ResourceNotFoundException("Estadística no encontrada con ID: " + id);
            }

            homeStatsRepository.deleteById(id);
            log.info("Estadística eliminada con ID: {}", id);
        }

        /**
         * Convierte entidad a DTO
         */
        private HomeStatsDTO convertToDTO(HomeStats stats) {
            return HomeStatsDTO.builder()
                    .id(stats.getId())
                    .label(stats.getLabel())
                    .valueText(stats.getValueText())
                    .icon(stats.getIcon())
                    .isActive(stats.getIsActive())
                    .build();
        }
    }
}
