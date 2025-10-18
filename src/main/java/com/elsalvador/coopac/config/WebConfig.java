package com.elsalvador.coopac.config;

import com.elsalvador.coopac.interceptor.RateLimitInterceptor;
import com.elsalvador.coopac.interceptor.RequestLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestLoggingInterceptor requestLoggingInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ✅ PROTECCIÓN: Rate limiting - debe ejecutarse PRIMERO
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/**", "/oauth2/**", "/login/**")
                .order(1);

        // Logging de peticiones
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/health", "/api/actuator/**")
                .order(2);
    }
}
