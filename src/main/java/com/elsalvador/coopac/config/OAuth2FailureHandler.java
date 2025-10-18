package com.elsalvador.coopac.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Handler personalizado para manejar fallos de autenticación OAuth2
 */
@Component
@Slf4j
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.error("❌ Fallo en autenticación OAuth2: {}", exception.getMessage());

        String errorMessage = "Error de autenticación. Por favor intente nuevamente.";

        // Personalizar mensaje según el tipo de error
        if (exception instanceof OAuth2AuthenticationException oauth2Exception) {
            String errorCode = oauth2Exception.getError().getErrorCode();

            errorMessage = switch (errorCode) {
                case "access_denied" -> "Acceso denegado. Solo el administrador puede acceder al sistema.";
                case "invalid_token" -> "Token inválido. Email ausente o formato incorrecto.";
                case "email_unverified" -> "Email no verificado. Por favor verifique su cuenta de Google.";
                case "invalid_issuer" -> "Proveedor de autenticación no autorizado.";
                case "token_expired" -> "Token expirado. Por favor intente nuevamente.";
                case "user_disabled" -> "Usuario deshabilitado. Contacte al administrador.";
                case "no_roles" -> "Usuario sin permisos. Contacte al administrador.";
                default -> oauth2Exception.getError().getDescription() != null ?
                           oauth2Exception.getError().getDescription() :
                           "Error de autenticación. Por favor intente nuevamente.";
            };
        }

        // Codificar mensaje para URL
        String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8);

        // Redirigir al frontend con mensaje de error
        String redirectUrl = frontendUrl + "/login?error=true&message=" + encodedMessage;

        log.info("Redirigiendo a: {}", redirectUrl);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}

