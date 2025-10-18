package com.elsalvador.coopac.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

/**
 * Configuración de cache para las páginas públicas
 * Cache se actualiza automáticamente cada 6 horas
 */
@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

    public static final String HOME_PAGE_CACHE = "homePageCache";
    public static final String ABOUT_PAGE_CACHE = "aboutPageCache";
    public static final String CONTACT_PAGE_CACHE = "contactPageCache";
    public static final String FINANCIAL_PAGE_CACHE = "financialPageCache";
    public static final String FOOTER_PAGE_CACHE = "footerPageCache";
    public static final String NAVIGATION_PAGE_CACHE = "navigationPageCache";
    public static final String PRODUCT_PAGE_CACHE = "productPageCache";
    public static final String PRODUCT_DETAIL_CACHE = "productDetailCache";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache(HOME_PAGE_CACHE),
                new ConcurrentMapCache(ABOUT_PAGE_CACHE),
                new ConcurrentMapCache(CONTACT_PAGE_CACHE),
                new ConcurrentMapCache(FINANCIAL_PAGE_CACHE),
                new ConcurrentMapCache(FOOTER_PAGE_CACHE),
                new ConcurrentMapCache(NAVIGATION_PAGE_CACHE),
                new ConcurrentMapCache(PRODUCT_PAGE_CACHE),
                new ConcurrentMapCache(PRODUCT_DETAIL_CACHE)
        ));
        return cacheManager;
    }

    /**
     * Limpia todos los caches cada 6 horas (21600000 ms)
     * Esto forzará a recargar los datos desde la base de datos
     */
    @Scheduled(fixedRate = 43200000) // 12 horas en milisegundos
    public void evictAllCaches() {
        cacheManager().getCacheNames()
                .forEach(cacheName -> {
                    var cache = cacheManager().getCache(cacheName);
                    if (cache != null) {
                        cache.clear();
                    }
                });
    }
}

