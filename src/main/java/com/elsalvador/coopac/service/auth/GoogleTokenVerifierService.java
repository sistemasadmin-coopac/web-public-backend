package com.elsalvador.coopac.service.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Servicio para verificar tokens de Google
 */
@Slf4j
@Service
public class GoogleTokenVerifierService {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifierService(@Value("${google.client.id}") String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory()
        )
        .setAudience(Collections.singletonList(clientId))
        .build();

        log.info("GoogleTokenVerifierService inicializado con Client ID: {}",
                clientId.substring(0, Math.min(20, clientId.length())) + "...");
    }

    /**
     * Verifica el token de Google y retorna el payload
     */
    public GoogleIdToken.Payload verifyToken(String tokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(tokenString);

            if (idToken == null) {
                log.warn("Token de Google inválido o expirado");
                throw new IllegalArgumentException("Token de Google inválido o expirado");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Verificar que el email esté verificado
            Boolean emailVerified = payload.getEmailVerified();
            if (emailVerified == null || !emailVerified) {
                log.warn("Email no verificado por Google: {}", payload.getEmail());
                throw new IllegalArgumentException("El email no está verificado por Google");
            }

            log.info("Token de Google verificado exitosamente para: {}", payload.getEmail());
            return payload;

        } catch (Exception e) {
            log.error("Error al verificar token de Google: {}", e.getMessage());
            throw new IllegalArgumentException("Error al verificar token de Google: " + e.getMessage());
        }
    }

    /**
     * Extrae el email del payload
     */
    public String getEmail(GoogleIdToken.Payload payload) {
        return payload.getEmail();
    }

    /**
     * Extrae el User ID de Google
     */
    public String getUserId(GoogleIdToken.Payload payload) {
        return payload.getSubject();
    }

    /**
     * Extrae el nombre del usuario
     */
    public String getName(GoogleIdToken.Payload payload) {
        return (String) payload.get("name");
    }

    /**
     * Extrae la URL de la foto de perfil
     */
    public String getPictureUrl(GoogleIdToken.Payload payload) {
        return (String) payload.get("picture");
    }
}

