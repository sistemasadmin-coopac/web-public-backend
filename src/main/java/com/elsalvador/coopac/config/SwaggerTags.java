package com.elsalvador.coopac.config;

/**
 * Constantes para los tags de Swagger/OpenAPI
 * Define los nombres y descripciones de las categorías de endpoints en la documentación
 */
public final class SwaggerTags {

    private SwaggerTags() {
        // Clase de constantes, no se debe instanciar
    }

    // ==================== ABOUT ====================
    /**
     * Tag para endpoints de administración de la sección About
     */
    public static final class About {
        public static final String TAG_NAME = "📄 About - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de la sección About: misión, visión, valores, historia, impacto y junta directiva";

        // Emojis para operaciones individuales
        public static final String EMOJI_GENERAL = "📄";
        public static final String EMOJI_MISSION = "🎯";
        public static final String EMOJI_VALUES = "💎";
        public static final String EMOJI_HISTORY = "📜";
        public static final String EMOJI_IMPACT = "📊";
        public static final String EMOJI_BOARD = "🏢";
    }

    // ==================== CONTACT ====================
    /**
     * Tag para endpoints de administración de contacto
     */
    public static final class Contact {
        public static final String TAG_NAME = "📞 Contact - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de la sección de contacto: canales, horarios y ubicaciones";

        // Emojis para operaciones individuales
        public static final String EMOJI_GENERAL = "📞";
        public static final String EMOJI_CHANNELS = "📧";
        public static final String EMOJI_SCHEDULE = "🕐";
        public static final String EMOJI_LOCATIONS = "📍";
    }

    // ==================== PRODUCTS ====================
    /**
     * Tag para endpoints de administración de productos
     */
    public static final class Products {
        public static final String TAG_NAME = "🛍️ Products - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de productos y categorías";

        public static final String EMOJI_GENERAL = "🛍️";
        public static final String EMOJI_CATEGORIES = "📂";
        public static final String EMOJI_ITEMS = "📦";
    }

    // ==================== HOME ====================
    /**
     * Tag para endpoints de administración de la página de inicio
     */
    public static final class Home {
        public static final String TAG_NAME = "🏠 Home - Administración";
        public static final String TAG_DESCRIPTION = "Gestión de la página de inicio: promociones, banners y contenido destacado";

        public static final String EMOJI_GENERAL = "🏠";
        public static final String EMOJI_PROMOTIONS = "🎉";
        public static final String EMOJI_BANNERS = "📢";
    }

    // ==================== FINANCIALS ====================
    /**
     * Tag para endpoints de administración de reportes financieros
     */
    public static final class Financials {
        public static final String TAG_NAME = "💰 Financials - Administración";
        public static final String TAG_DESCRIPTION = "Gestión de reportes financieros y documentos relacionados";

        public static final String EMOJI_GENERAL = "💰";
        public static final String EMOJI_REPORTS = "📊";
    }

    // ==================== SITE ====================
    /**
     * Tag para endpoints de administración del sitio
     */
    public static final class Site {
        public static final String TAG_NAME = "⚙️ Site - Administración";
        public static final String TAG_DESCRIPTION = "Configuración general del sitio web";

        public static final String EMOJI_GENERAL = "⚙️";
        public static final String EMOJI_CONFIG = "🔧";
    }

    // ==================== AUTH ====================
    /**
     * Tag para endpoints de autenticación
     */
    public static final class Auth {
        public static final String TAG_NAME = "🔐 Autenticación";
        public static final String TAG_DESCRIPTION = "Endpoints de autenticación y autorización";

        public static final String EMOJI_LOGIN = "🔑";
        public static final String EMOJI_LOGOUT = "🚪";
        public static final String EMOJI_PROFILE = "👤";
    }
}

