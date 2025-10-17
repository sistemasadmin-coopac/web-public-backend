package com.elsalvador.coopac.config;

import com.elsalvador.coopac.service.CustomOidcUserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;
    private final CorsProperties corsProperties;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configurar gestión de sesión
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // DESHABILITAR CSRF temporalmente para pruebas (reactivar después)
                .csrf(AbstractHttpConfigurer::disable)

                // Configurar CORS ANTES de las autorizaciones
                .cors(Customizer.withDefaults())

                // Configurar autorización de endpoints
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos - accesibles sin autenticación
                        .requestMatchers(
                                "/api/public/**",
                                "/api/home",
                                "/api/products/**",
                                "/api/financials/**",
                                "/api/site/**",
                                "/api/navigation/**",
                                "/api/footer/**",
                                "/api/contact/**",
                                "/api/about/**",
                                "/actuator/health",
                                "/login/**",
                                "/oauth2/**",
                                "/error",
                                "/",
                                "/health"
                        ).permitAll()
                        // Endpoints de administración (requieren rol ADMIN)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Endpoints de autenticación (requieren login)
                        .requestMatchers("/api/auth/**").authenticated()
                        .anyRequest().authenticated()
                )
                // Configurar OAuth2 Login
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService)
                        )
                        .defaultSuccessUrl(frontendUrl + "/admin", true)
                        .failureUrl(frontendUrl + "/login?error=true")
                )
                // Configurar logout
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\":\"Sesión cerrada correctamente\"}");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("SESSIONID", "XSRF-TOKEN")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Redirigir al frontend después del login exitoso
            response.sendRedirect(frontendUrl + "/admin");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Usar los orígenes configurados en application.yml
        configuration.setAllowedOrigins(corsProperties.getOrigins());

        // Métodos permitidos
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Headers permitidos (incluir X-XSRF-TOKEN para CSRF)
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-XSRF-TOKEN",
                "X-Requested-With",
                "Accept"
        ));

        // Headers expuestos (incluir CSRF token para que el frontend pueda leerlo)
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-XSRF-TOKEN"
        ));

        // CRÍTICO: Permitir credenciales (cookies) entre dominios
        configuration.setAllowCredentials(true);

        // Max age para preflight
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
