package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.navigation.NavigationDTO;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.navigation.NavigationMenusRepository;
import com.elsalvador.coopac.service.publicpages.GetDataNavigationPageService;
import com.elsalvador.coopac.service.publicpages.mapper.navigation.NavigationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.NAVIGATION_PAGE_CACHE;

/**
 * Servicio para Navigation que orquesta todos los datos necesarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataNavigationPageServiceImpl implements GetDataNavigationPageService {

    // Repositorios
    private final NavigationMenusRepository navigationMenusRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    // Mapper
    private final NavigationMapper navigationMapper;

    /**
     * Obtiene los datos de navegación principal
     * Cache se actualiza cada 6 horas
     */
    @Cacheable(value = NAVIGATION_PAGE_CACHE, key = "'main'")
    public NavigationDTO getMainNavigation() {
        log.debug("Obteniendo datos de navegación principal desde la base de datos");

        var menu = navigationMenusRepository
            .findBySlugAndIsActiveTrueWithItems("main")
            .orElse(null);

        if (menu == null) {
            log.warn("No se encontró menú principal activo");
            return createDefaultNavigation();
        }

        // Obtener configuraciones del sitio para brand y shortcuts
        var siteSettings = siteSettingsRepository.findAll().stream().findFirst().orElse(null);

        String brandUrl = "/";
        String logoUrl = siteSettings != null ? siteSettings.getLogoUrl() : "";
        String logoAlt = siteSettings != null ? siteSettings.getCompanyName() : "COOPAC El Salvador";
        String whatsappUrl = siteSettings != null ? siteSettings.getWhatsappUrl() : "";
        String phone = siteSettings != null ? siteSettings.getPhoneMain() : "";

        return navigationMapper.mapNavigation(menu, brandUrl, logoUrl, logoAlt, whatsappUrl, phone);
    }

    /**
     * Crea navegación por defecto como fallback
     */
    private NavigationDTO createDefaultNavigation() {
        return new NavigationDTO(
                new NavigationDTO.MenuDTO("main", "Principal"),
                new NavigationDTO.BrandDTO("/", "", "COOPAC El Salvador"),
                java.util.List.of(),
                new NavigationDTO.ShortcutsDTO("", "")
        );
    }
}
