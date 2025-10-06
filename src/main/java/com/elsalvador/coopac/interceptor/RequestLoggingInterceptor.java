package com.elsalvador.coopac.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Interceptor para logging y monitoreo de solicitudes HTTP.
 * Captura información sobre cada request/response para debugging y auditoría.
 */
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final String REQUEST_START_TIME = "REQUEST_START_TIME";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // Marcar el tiempo de inicio de la solicitud
        request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());

        // Log de la solicitud entrante
        logRequest(request);

        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) {
        // Calcular tiempo de procesamiento
        Long startTime = (Long) request.getAttribute(REQUEST_START_TIME);
        long duration = startTime != null ? System.currentTimeMillis() - startTime : 0;

        // Log de la respuesta
        logResponse(request, response, duration, ex);
    }

    private void logRequest(HttpServletRequest request) {
        var timestamp = LocalDateTime.now().format(FORMATTER);
        var method = request.getMethod();
        var uri = request.getRequestURI();
        var queryString = request.getQueryString();
        var userAgent = request.getHeader("User-Agent");
        var remoteAddr = getClientIpAddress(request);

        var logMessage = String.format(
            "[%s] INCOMING REQUEST: %s %s%s | IP: %s | User-Agent: %s",
            timestamp,
            method,
            uri,
            queryString != null ? "?" + queryString : "",
            remoteAddr,
            userAgent != null ? userAgent.substring(0, Math.min(userAgent.length(), 100)) : "N/A"
        );

        System.out.println(logMessage);
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, long duration, Exception ex) {
        var timestamp = LocalDateTime.now().format(FORMATTER);
        var method = request.getMethod();
        var uri = request.getRequestURI();
        var status = response.getStatus();
        var contentType = response.getContentType();

        String logMessage;
        if (ex != null) {
            logMessage = String.format(
                "[%s] REQUEST COMPLETED WITH EXCEPTION: %s %s | Status: %d | Duration: %dms | Exception: %s",
                timestamp,
                method,
                uri,
                status,
                duration,
                ex.getClass().getSimpleName() + ": " + ex.getMessage()
            );
            System.err.println(logMessage);
        } else {
            logMessage = String.format(
                "[%s] REQUEST COMPLETED: %s %s | Status: %d | Duration: %dms | Content-Type: %s",
                timestamp,
                method,
                uri,
                status,
                duration,
                contentType != null ? contentType : "N/A"
            );
            System.out.println(logMessage);
        }

        // Log de solicitudes lentas (más de 5 segundos)
        if (duration > 5000) {
            System.err.println("[WARNING] SLOW REQUEST DETECTED: " + uri + " took " + duration + "ms");
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}
