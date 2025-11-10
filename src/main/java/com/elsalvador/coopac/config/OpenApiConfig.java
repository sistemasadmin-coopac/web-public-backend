package com.elsalvador.coopac.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("CAC El Salvador Backend API")
                .version("1.0.0")
                .description("API de administración para el sitio web de CAC El Salvador")
                .contact(new Contact()
                    .name("CAC El Salvador")
                    .url("https://www.cac-elsalvador.org")
                )
            );
    }
}

