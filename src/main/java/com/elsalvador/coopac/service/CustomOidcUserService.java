package com.elsalvador.coopac.service;

import com.elsalvador.coopac.entity.security.Role;
import com.elsalvador.coopac.entity.security.User;
import com.elsalvador.coopac.repository.RoleRepository;
import com.elsalvador.coopac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Email permitido para autenticación
    private static final String ALLOWED_EMAIL = "sistemas_admin@coopacelsalvador.com";

    // Issuer esperado de Google
    private static final String EXPECTED_ISSUER = "https://accounts.google.com";

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        try {
            log.info("=== Iniciando autenticación OAuth2 con Google ===");

            // Cargar usuario desde Google
            OidcUser oidcUser = super.loadUser(userRequest);

            // ✅ VALIDACIÓN 1: Verificar que el email existe
            String email = oidcUser.getEmail();
            if (email == null || email.isBlank()) {
                log.error("❌ Email ausente en el token OIDC");
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_token", "Email ausente en el token", null),
                    "Token inválido: email no proporcionado"
                );
            }

            log.info("Usuario de Google obtenido: {}", email);

            // ✅ VALIDACIÓN 2: Verificar que el email está verificado por Google
            Object emailVerifiedClaim = userRequest.getIdToken().getClaims().get("email_verified");
            boolean emailVerified = false;
            if (emailVerifiedClaim instanceof Boolean) {
                emailVerified = (Boolean) emailVerifiedClaim;
            } else if (emailVerifiedClaim != null) {
                emailVerified = Boolean.parseBoolean(emailVerifiedClaim.toString());
            }

            if (!emailVerified) {
                log.error("❌ Email no verificado para: {}", email);
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("email_unverified", "El email no está verificado por Google", null),
                    "El email " + email + " no está verificado. Verifica tu cuenta de Google."
                );
            }

            log.info("✅ Email verificado por Google: {}", email);

            // ✅ VALIDACIÓN 3: Verificar el issuer del token
            String issuer = userRequest.getIdToken().getIssuer().toString();
            if (!EXPECTED_ISSUER.equals(issuer)) {
                log.error("❌ Issuer inválido. Esperado: {}, Recibido: {}", EXPECTED_ISSUER, issuer);
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_issuer", "Issuer del token no es válido", null),
                    "Token no proviene de Google"
                );
            }

            log.info("✅ Issuer válido: {}", issuer);

            // ✅ VALIDACIÓN 4: Verificar que el token no ha expirado
            if (userRequest.getIdToken().getExpiresAt() != null &&
                userRequest.getIdToken().getExpiresAt().isBefore(java.time.Instant.now())) {
                log.error("❌ Token expirado");
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("token_expired", "El token ha expirado", null),
                    "Token expirado"
                );
            }

            // ✅ VALIDACIÓN 5: Solo permitir el email autorizado
            if (!ALLOWED_EMAIL.equalsIgnoreCase(email)) {
                log.warn("❌ Acceso denegado para el email: {}. Solo se permite: {}", email, ALLOWED_EMAIL);
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("access_denied",
                        "Acceso no autorizado. Solo se permite el usuario administrador.",
                        null),
                    "El correo " + email + " no tiene permisos para acceder al sistema."
                );
            }

            log.info("✅ Email autorizado: {}", email);

            // ✅ VALIDACIÓN 6: Verificar que el Subject existe (identificador único de Google)
            String googleSub = oidcUser.getSubject();
            if (googleSub == null || googleSub.isBlank()) {
                log.error("❌ Subject (googleSub) ausente en el token");
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("invalid_token", "Subject ausente en el token", null),
                    "Token inválido: subject no proporcionado"
                );
            }

            // Sincronizar con la base de datos
            User user = syncUser(oidcUser);

            // ✅ VALIDACIÓN 7: Verificar que el usuario esté habilitado
            if (!user.getEnabled()) {
                log.error("❌ Usuario deshabilitado: {}", email);
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("user_disabled", "Usuario deshabilitado", null),
                    "El usuario ha sido deshabilitado"
                );
            }

            // Construir autoridades desde los roles de BD
            Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toSet());

            // ✅ VALIDACIÓN 8: Verificar que el usuario tiene roles asignados
            if (authorities.isEmpty()) {
                log.error("❌ Usuario sin roles asignados: {}", email);
                throw new OAuth2AuthenticationException(
                    new OAuth2Error("no_roles", "Usuario sin roles asignados", null),
                    "El usuario no tiene roles asignados"
                );
            }

            log.info("✅ Usuario autenticado exitosamente: {} con roles: {}", user.getEmail(), authorities);

            // Retornar OidcUser con las autoridades personalizadas
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        } catch (OAuth2AuthenticationException e) {
            log.error("❌ Error de autenticación OAuth2: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ Error durante la autenticación OAuth2: {}", e.getMessage(), e);
            throw new OAuth2AuthenticationException(
                new OAuth2Error("server_error", "Error al autenticar usuario", null),
                "Error al autenticar usuario: " + e.getMessage()
            );
        }
    }

    private User syncUser(OidcUser oidcUser) {
        String googleSub = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String pictureUrl = oidcUser.getPicture();

        User user = userRepository.findByGoogleSub(googleSub)
                .orElseGet(() -> {
                    log.info("Creando nuevo usuario desde Google OAuth: {}", email);
                    User newUser = new User();
                    newUser.setGoogleSub(googleSub);
                    newUser.setEmail(email);
                    newUser.setEnabled(true);

                    // Asignar rol ADMIN al usuario autorizado
                    Role adminRole = roleRepository.findByName("ADMIN")
                            .orElseGet(() -> {
                                log.info("Creando rol ADMIN");
                                Role role = new Role();
                                role.setName("ADMIN");
                                role.setDescription("Administrador del sistema");
                                return roleRepository.save(role);
                            });

                    Set<Role> roles = new HashSet<>();
                    roles.add(adminRole);
                    newUser.setRoles(roles);

                    return newUser;
                });

        // Actualizar información del usuario
        user.setName(name);
        user.setPictureUrl(pictureUrl);
        user.setLastLogin(LocalDateTime.now());

        return userRepository.save(user);
    }
}
