package com.elsalvador.coopac.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handler personalizado para redirigir al frontend después de login exitoso con OAuth2
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("Login exitoso para usuario: {}", authentication.getName());

        // Redirigir al frontend con la sesión ya creada
        String redirectUrl = frontendUrl + "/admin";

        log.info("Redirigiendo a: {}", redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}

