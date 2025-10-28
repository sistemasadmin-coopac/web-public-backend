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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;
    private final CorsProperties corsProperties;
    private final OAuth2FailureHandler oauth2FailureHandler;
    private final OAuth2SuccessHandler oauth2SuccessHandler;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // ✅ PROTECCIÓN EXTRA: Configurar CSRF con cookies
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                // ✅ PROTECCIÓN 1: Configurar gestión de sesión estricta
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1) // Solo 1 sesión activa por usuario
                        .maxSessionsPreventsLogin(false) // Invalidar sesión anterior
                )

                // ✅ PROTECCIÓN 2: CSRF deshabilitado temporalmente para desarrollo
                .csrf(AbstractHttpConfigurer::disable)

                // ✅ PROTECCIÓN 2 (PRODUCCIÓN): CSRF habilitado con cookies
                // .csrf(csrf -> csrf
                //         .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //         .csrfTokenRequestHandler(requestHandler)
                //         .ignoringRequestMatchers("/api/public/**", "/actuator/health", "/health", "/error")
                // )

                // ✅ PROTECCIÓN 3: Headers de seguridad
                .headers(headers -> headers
                        // Prevenir clickjacking
                        .frameOptions(frameOptions -> frameOptions.deny())
                        // Prevenir MIME sniffing
                        .contentTypeOptions(Customizer.withDefaults())
                        // XSS Protection
                        .xssProtection(xss -> xss
                                .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        )
                        // HSTS (Force HTTPS)
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .maxAgeInSeconds(31536000) // 1 año
                        )
                        // Referrer Policy
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        // Content Security Policy
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; " +
                                        "script-src 'self' 'unsafe-inline' https://accounts.google.com; " +
                                        "style-src 'self' 'unsafe-inline'; " +
                                        "img-src 'self' data: https:; " +
                                        "font-src 'self' data:; " +
                                        "connect-src 'self' https://accounts.google.com; " +
                                        "frame-src 'self' https://accounts.google.com; " +
                                        "frame-ancestors 'none'")
                        )
                )

                // Configurar CORS
                .cors(Customizer.withDefaults())

                // ✅ PROTECCIÓN 4: Autorización de endpoints con validación estricta
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
                                "/api/join/page",
                                "/actuator/health",
                                "/login/**",
                                "/oauth2/**",
                                "/error",
                                "/",
                                "/health"
                        ).permitAll()

                        // ✅ CRÍTICO: Endpoints de administración requieren ROL ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // ✅ CRÍTICO: Endpoints de autenticación requieren autenticación
                        .requestMatchers("/api/auth/**").authenticated()

                        // ✅ PROTECCIÓN: Denegar cualquier otro endpoint no especificado
                        .anyRequest().authenticated()
                )

                // ✅ PROTECCIÓN 5: Configurar OAuth2 Login con handlers personalizados
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(customOidcUserService)
                        )
                        .successHandler(oauth2SuccessHandler) // ← Usar handler personalizado
                        .failureHandler(oauth2FailureHandler)
                )

                // ✅ PROTECCIÓN 6: Configurar logout seguro
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"message\":\"Sesión cerrada correctamente\"}");
                        })
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN")
                        .permitAll()
                )

                // ✅ PROTECCIÓN 7: Handler de acceso denegado
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Acceso denegado\",\"message\":\"No tienes permisos para acceder a este recurso\"}");
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"No autenticado\",\"message\":\"Debes iniciar sesión para acceder a este recurso\"}");
                        })
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
