package com.elsalvador.coopac.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interceptor para limitar la tasa de peticiones y prevenir ataques de fuerza bruta
 */
@Component
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

    // Almacenar intentos por IP
    private final Map<String, RequestInfo> requestCache = new ConcurrentHashMap<>();

    // Configuración
    private static final int MAX_REQUESTS_PER_MINUTE = 1000; // Máximo 1000 peticiones por minuto (aumentado)
    private static final int MAX_AUTH_ATTEMPTS = 20; // Máximo 20 intentos de autenticación por hora (aumentado)
    private static final Duration RATE_LIMIT_WINDOW = Duration.ofMinutes(1);
    private static final Duration AUTH_LIMIT_WINDOW = Duration.ofHours(1);
    private static final Duration BLOCK_DURATION = Duration.ofMinutes(5); // Bloquear por 5 minutos (reducido)

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = getClientIP(request);
        String requestUri = request.getRequestURI();

        // Limpiar entradas antiguas periódicamente
        cleanupOldEntries();

        // Verificar si la IP está bloqueada
        if (isBlocked(clientIp)) {
            log.warn("🚫 IP bloqueada intentando acceder: {} - URI: {}", clientIp, requestUri);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Too Many Requests\",\"message\":\"Has sido bloqueado temporalmente por exceder el límite de peticiones. Intenta nuevamente en 15 minutos.\"}");
            return false;
        }

        // Aplicar rate limiting general
        if (isRateLimitExceeded(clientIp)) {
            log.warn("⚠️ Rate limit excedido para IP: {} - URI: {}", clientIp, requestUri);
            blockIP(clientIp);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Too Many Requests\",\"message\":\"Has excedido el límite de peticiones. Intenta nuevamente más tarde.\"}");
            return false;
        }

        // Protección extra para endpoints de autenticación
        if (isAuthEndpoint(requestUri)) {
            if (isAuthLimitExceeded(clientIp)) {
                log.warn("🔒 Intentos de autenticación excedidos para IP: {} - URI: {}", clientIp, requestUri);
                blockIP(clientIp);
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Too Many Requests\",\"message\":\"Demasiados intentos de inicio de sesión. Intenta nuevamente en 15 minutos.\"}");
                return false;
            }
            incrementAuthAttempts(clientIp);
        }

        // Registrar la petición
        incrementRequestCount(clientIp);

        return true;
    }

    /**
     * Obtiene la IP del cliente, considerando proxies
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0].trim();
        }

        String xRealIP = request.getHeader("X-Real-IP");
        if (xRealIP != null && !xRealIP.isEmpty()) {
            return xRealIP;
        }

        return request.getRemoteAddr();
    }

    /**
     * Verifica si la IP está bloqueada
     */
    private boolean isBlocked(String ip) {
        RequestInfo info = requestCache.get(ip);
        if (info == null) {
            return false;
        }

        if (info.blockedUntil != null && Instant.now().isBefore(info.blockedUntil)) {
            return true;
        }

        // Si el bloqueo expiró, limpiarlo
        if (info.blockedUntil != null && Instant.now().isAfter(info.blockedUntil)) {
            info.blockedUntil = null;
            info.requestCount = 0;
            info.authAttempts = 0;
        }

        return false;
    }

    /**
     * Bloquea una IP temporalmente
     */
    private void blockIP(String ip) {
        RequestInfo info = requestCache.computeIfAbsent(ip, k -> new RequestInfo());
        info.blockedUntil = Instant.now().plus(BLOCK_DURATION);
        log.error("🚨 IP bloqueada: {} hasta {}", ip, info.blockedUntil);
    }

    /**
     * Verifica si se excedió el rate limit general
     */
    private boolean isRateLimitExceeded(String ip) {
        RequestInfo info = requestCache.get(ip);
        if (info == null) {
            return false;
        }

        // Si la ventana de tiempo expiró, resetear contador
        if (Instant.now().isAfter(info.lastRequest.plus(RATE_LIMIT_WINDOW))) {
            info.requestCount = 0;
            info.lastRequest = Instant.now();
            return false;
        }

        return info.requestCount >= MAX_REQUESTS_PER_MINUTE;
    }

    /**
     * Verifica si se excedió el límite de intentos de autenticación
     */
    private boolean isAuthLimitExceeded(String ip) {
        RequestInfo info = requestCache.get(ip);
        if (info == null) {
            return false;
        }

        // Si la ventana de tiempo expiró, resetear contador
        if (Instant.now().isAfter(info.lastAuthAttempt.plus(AUTH_LIMIT_WINDOW))) {
            info.authAttempts = 0;
            info.lastAuthAttempt = Instant.now();
            return false;
        }

        return info.authAttempts >= MAX_AUTH_ATTEMPTS;
    }

    /**
     * Incrementa el contador de peticiones
     */
    private void incrementRequestCount(String ip) {
        RequestInfo info = requestCache.computeIfAbsent(ip, k -> new RequestInfo());
        info.requestCount++;
        info.lastRequest = Instant.now();
    }

    /**
     * Incrementa el contador de intentos de autenticación
     */
    private void incrementAuthAttempts(String ip) {
        RequestInfo info = requestCache.computeIfAbsent(ip, k -> new RequestInfo());
        info.authAttempts++;
        info.lastAuthAttempt = Instant.now();
    }

    /**
     * Verifica si es un endpoint de autenticación
     */
    private boolean isAuthEndpoint(String uri) {
        return uri.contains("/oauth2/") ||
               uri.contains("/login") ||
               uri.contains("/api/auth/");
    }

    /**
     * Limpia entradas antiguas del cache (ejecutar periódicamente)
     */
    private void cleanupOldEntries() {
        Instant cutoff = Instant.now().minus(Duration.ofHours(2));
        requestCache.entrySet().removeIf(entry -> {
            RequestInfo info = entry.getValue();
            return info.lastRequest.isBefore(cutoff) &&
                   (info.blockedUntil == null || info.blockedUntil.isBefore(Instant.now()));
        });
    }

    /**
     * Clase interna para almacenar información de peticiones por IP
     */
    private static class RequestInfo {
        int requestCount = 0;
        int authAttempts = 0;
        Instant lastRequest = Instant.now();
        Instant lastAuthAttempt = Instant.now();
        Instant blockedUntil = null;
    }
}

