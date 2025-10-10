package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.footer.FooterDTO;
import com.elsalvador.coopac.entity.config.SiteSettings;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.repository.navigation.FooterColumnsRepository;
import com.elsalvador.coopac.service.publicpages.GetDataFooterPageService;
import com.elsalvador.coopac.service.publicpages.mapper.footer.FooterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para Footer que orquesta todos los datos necesarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataFooterPageServiceImpl implements GetDataFooterPageService {

    // Repositorios
    private final FooterColumnsRepository footerColumnsRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    // Mapper
    private final FooterMapper footerMapper;

    /**
     * Obtiene los datos del footer
     */
    public FooterDTO getFooterData() {
        log.debug("Obteniendo datos del footer");

        var columns = footerColumnsRepository.findByIsActiveTrueWithLinksOrderByDisplayOrderAsc();

        if (columns.isEmpty()) {
            log.warn("No se encontraron columnas del footer activas");
        }

        // Obtener configuraciones del sitio
        var siteSettings = siteSettingsRepository.findAll().stream().findFirst().orElse(null);

        String companyName = siteSettings != null ? siteSettings.getCompanyName() : "COOPAC El Salvador";
        String logoUrl = siteSettings != null ? siteSettings.getLogoUrl() : "";
        String logoAlt = siteSettings != null ? siteSettings.getLogoAlt() : companyName;
        String phone = siteSettings != null ? siteSettings.getPhoneMain() : "";
        String email = siteSettings != null ? siteSettings.getEmailMain() : "";
        String address = buildFullAddress(siteSettings);
        String facebook = siteSettings != null ? siteSettings.getFacebookUrl() : "";
        String instagram = siteSettings != null ? siteSettings.getInstagramUrl() : "";
        String linkedin = siteSettings != null ? siteSettings.getLinkedinUrl() : "";
        String twitter = siteSettings != null ? siteSettings.getTwitterUrl() : "";

        return footerMapper.mapFooter(columns, companyName, logoUrl, logoAlt, phone, email, address,
                                    facebook, instagram, linkedin, twitter);
    }

    /**
     * Construye la dirección completa desde site_settings
     */
    private String buildFullAddress(SiteSettings settings) {
        if (settings == null) return "";

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
        if (settings.getCountry() != null) {
            if (!address.isEmpty()) address.append(", ");
            address.append(settings.getCountry());
        }

        return address.toString();
    }
}
