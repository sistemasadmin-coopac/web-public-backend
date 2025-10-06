package com.elsalvador.coopac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuración para servir archivos estáticos desde el sistema de archivos local
 */
@Configuration
public class FileStorageConfig implements WebMvcConfigurer {

    @Value("${file.storage.local.base-path:./uploads}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        String uploadPathStr = uploadDir.toUri().toString();

        // Servir archivos desde la carpeta de uploads
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPathStr);
    }
}

