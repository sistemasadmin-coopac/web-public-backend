package com.elsalvador.coopac.config;

/**
 * Constantes para los tags de Swagger/OpenAPI
 * Define los nombres y descripciones de las categorías de endpoints en la documentación
 */
public final class SwaggerTags {

    private SwaggerTags() {
        // Clase de constantes, no se debe instanciar
    }

    public static final class About {
        public static final String TAG_NAME = "📄 About - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de la sección About: misión, visión, valores, historia, impacto y junta directiva";
        public static final String EMOJI_GENERAL = "📄";
        public static final String EMOJI_MISSION = "🎯";
        public static final String EMOJI_VALUES = "💎";
        public static final String EMOJI_HISTORY = "📜";
        public static final String EMOJI_IMPACT = "📊";
        public static final String EMOJI_BOARD = "🏢";
    }

    public static final class Contact {
        public static final String TAG_NAME = "📞 Contact - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de la sección de contacto: canales, horarios y ubicaciones";
        public static final String EMOJI_GENERAL = "📞";
        public static final String EMOJI_CHANNELS = "📧";
        public static final String EMOJI_SCHEDULE = "🕐";
        public static final String EMOJI_LOCATIONS = "📍";
    }

    public static final class Products {
        public static final String TAG_NAME = "📦 Productos - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de productos: categorías, características, acciones, badges, pasos e información financiera";
        public static final String EMOJI_GENERAL = "📦";
        public static final String EMOJI_CATEGORIES = "📂";
        public static final String EMOJI_FEATURES = "✨";
        public static final String EMOJI_ACTIONS = "🎯";
        public static final String EMOJI_BADGES = "🏷️";
        public static final String EMOJI_STEPS = "📍";
        public static final String EMOJI_FINANCIAL = "💰";
    }

    public static final class Home {
        public static final String TAG_NAME = "🏠 Home - Administración";
        public static final String TAG_DESCRIPTION = "Gestión de la página de inicio: promociones, bloques CTA, estadísticas";
        public static final String EMOJI_GENERAL = "🏠";
        public static final String EMOJI_PROMOTIONS = "🎯";
        public static final String EMOJI_CTA_BLOCKS = "📢";
        public static final String EMOJI_STATS = "📊";
    }

    public static final class Financials {
        public static final String TAG_NAME = "💰 Financials - Administración";
        public static final String TAG_DESCRIPTION = "Gestión completa de reportes y categorías financieras";
        public static final String EMOJI_GENERAL = "💰";
        public static final String EMOJI_CATEGORIES = "📂";
        public static final String EMOJI_REPORTS = "📄";
    }

    public static final class Headers {
        public static final String TAG_NAME = "🎨 Headers - Administración";
        public static final String TAG_DESCRIPTION = "Gestión de headers y tarjetas de encabezados de páginas";
        public static final String EMOJI_GENERAL = "🎨";
        public static final String EMOJI_PAGE_HEADERS = "📑";
        public static final String EMOJI_CARDS = "🗂️";
    }

    public static final class Site {
        public static final String TAG_NAME = "⚙️ Settings - Administración";
        public static final String TAG_DESCRIPTION = "Gestión de configuración general del sitio: contactos, redes sociales, ubicación";
        public static final String EMOJI_GENERAL = "⚙️";
        public static final String EMOJI_CONFIG = "🔧";
    }

    public static final class Auth {
        public static final String TAG_NAME = "🔐 Autenticación";
        public static final String TAG_DESCRIPTION = "Endpoints de autenticación y autorización";
        public static final String EMOJI_LOGIN = "🔑";
        public static final String EMOJI_LOGOUT = "🚪";
        public static final String EMOJI_PROFILE = "👤";
    }

    public static final class Dashboard {
        public static final String TAG_NAME = "📊 Dashboard";
        public static final String TAG_DESCRIPTION = "Endpoints del panel de administración";
        public static final String EMOJI_GENERAL = "📊";
        public static final String EMOJI_STATS = "📈";
    }

    public static final class PublicPages {
        public static final String TAG_NAME = "🌐 Páginas Públicas";
        public static final String TAG_DESCRIPTION = "Endpoints públicos para obtener datos de las páginas del sitio";
        public static final String EMOJI_GENERAL = "🌐";
        public static final String EMOJI_ABOUT = "ℹ️";
        public static final String EMOJI_CONTACT = "📧";
        public static final String EMOJI_HOME = "🏠";
        public static final String EMOJI_JOIN = "🤝";
        public static final String EMOJI_PRODUCTS = "📦";
    }

    public static final class Join {
        public static final String TAG_NAME = "🤝 Join - Administración";
        public static final String TAG_DESCRIPTION = "Gestión de la sección Join/Asóciate Ya: beneficios, costos, requisitos y beneficios especiales";
        public static final String EMOJI_GENERAL = "🤝";
        public static final String EMOJI_BENEFITS = "🎁";
        public static final String EMOJI_COSTS = "💵";
        public static final String EMOJI_REQUIREMENTS = "📋";
        public static final String EMOJI_SPECIAL_BENEFITS = "⭐";
    }
}

