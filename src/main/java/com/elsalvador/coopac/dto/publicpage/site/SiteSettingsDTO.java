package com.elsalvador.coopac.dto.publicpage.site;

import java.time.ZonedDateTime;

/**
 * DTO para la configuración global del sitio web.
 *
 * @param company información de la empresa
 * @param contact datos de contacto principales
 * @param social redes sociales
 * @param updatedAt última actualización de la configuración
 */
public record SiteSettingsDTO(
    CompanyDTO company,
    ContactDTO contact,
    SocialDTO social,
    ZonedDateTime updatedAt
) {

    public SiteSettingsDTO {
        if (company == null) throw new IllegalArgumentException("company es requerido");
        if (contact == null) throw new IllegalArgumentException("contact es requerido");
        if (social == null) throw new IllegalArgumentException("social es requerido");
        if (updatedAt == null) throw new IllegalArgumentException("updatedAt es requerido");
    }

    /**
     * Información básica de la empresa.
     */
    public record CompanyDTO(
        String name,
        LogoDTO logo
    ) {
        public CompanyDTO {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name es requerido");
            }
            if (logo == null) {
                throw new IllegalArgumentException("logo es requerido");
            }
        }
    }

    /**
     * Información del logo de la empresa.
     */
    public record LogoDTO(
        String url,
        String alt
    ) {
        public LogoDTO {
            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("url del logo es requerido");
            }
            if (alt == null || alt.isBlank()) {
                throw new IllegalArgumentException("alt del logo es requerido");
            }
        }

        public boolean isSecure() {
            return url.startsWith("https://");
        }

        public boolean isCDN() {
            return url.contains("cdn.");
        }
    }

    /**
     * Información de contacto principal de la empresa.
     */
    public record ContactDTO(
        String phoneMain,
        String phoneSecondary, // Puede ser null
        WhatsAppDTO whatsapp,
        String emailMain,
        String emailSupport, // Puede ser null
        AddressDTO address,
        String googleMapsUrl
    ) {
        public ContactDTO {
            if (phoneMain == null || phoneMain.isBlank()) {
                throw new IllegalArgumentException("phoneMain es requerido");
            }
            if (emailMain == null || emailMain.isBlank()) {
                throw new IllegalArgumentException("emailMain es requerido");
            }
            if (whatsapp == null) {
                throw new IllegalArgumentException("whatsapp es requerido");
            }
            if (address == null) {
                throw new IllegalArgumentException("address es requerido");
            }
        }

        public boolean hasSecondaryPhone() {
            return phoneSecondary != null && !phoneSecondary.isBlank();
        }

        public boolean hasSupportEmail() {
            return emailSupport != null && !emailSupport.isBlank();
        }

        public String getMainPhoneFormatted() {
            return phoneMain.replaceAll("[^0-9]", "");
        }
    }

    /**
     * Información de WhatsApp.
     */
    public record WhatsAppDTO(
        String number,
        String url
    ) {
        public WhatsAppDTO {
            if (number == null || number.isBlank()) {
                throw new IllegalArgumentException("WhatsApp number es requerido");
            }
            if (url == null || url.isBlank()) {
                throw new IllegalArgumentException("WhatsApp url es requerido");
            }
            if (!url.startsWith("https://wa.me/")) {
                throw new IllegalArgumentException("WhatsApp url debe comenzar con https://wa.me/");
            }
        }

        public String getFormattedNumber() {
            return "+" + number;
        }

        public String getClickToCallUrl() {
            return "tel:+" + number;
        }
    }

    /**
     * Dirección física de la empresa.
     */
    public record AddressDTO(
        String line1,
        String line2, // Puede ser null
        String city,
        String state,
        String country
    ) {
        public AddressDTO {
            if (line1 == null || line1.isBlank()) {
                throw new IllegalArgumentException("address line1 es requerido");
            }
            if (city == null || city.isBlank()) {
                throw new IllegalArgumentException("city es requerido");
            }
            if (state == null || state.isBlank()) {
                throw new IllegalArgumentException("state es requerido");
            }
            if (country == null || country.isBlank()) {
                throw new IllegalArgumentException("country es requerido");
            }
        }

        public boolean hasLine2() {
            return line2 != null && !line2.isBlank();
        }

        public String getFullAddress() {
            var address = line1;
            if (hasLine2()) {
                address = address + ", " + line2;
            }
            return address + ", " + city + ", " + state + ", " + country;
        }

        public String getCityState() {
            return city + ", " + state;
        }
    }

    /**
     * Enlaces a redes sociales.
     */
    public record SocialDTO(
        String facebook,
        String instagram,
        String linkedin,
        String twitter // Puede ser null
    ) {
        public SocialDTO {
            if (facebook == null || facebook.isBlank()) {
                throw new IllegalArgumentException("facebook es requerido");
            }
            if (instagram == null || instagram.isBlank()) {
                throw new IllegalArgumentException("instagram es requerido");
            }
            if (linkedin == null || linkedin.isBlank()) {
                throw new IllegalArgumentException("linkedin es requerido");
            }
        }

        public boolean hasTwitter() {
            return twitter != null && !twitter.isBlank();
        }

        public int getActiveNetworksCount() {
            return (facebook != null ? 1 : 0) +
                   (instagram != null ? 1 : 0) +
                   (linkedin != null ? 1 : 0) +
                   (hasTwitter() ? 1 : 0);
        }

        public boolean isValidFacebookUrl() {
            return facebook.startsWith("https://facebook.com/") || facebook.startsWith("https://www.facebook.com/");
        }

        public boolean isValidInstagramUrl() {
            return instagram.startsWith("https://instagram.com/") || instagram.startsWith("https://www.instagram.com/");
        }
    }
}
