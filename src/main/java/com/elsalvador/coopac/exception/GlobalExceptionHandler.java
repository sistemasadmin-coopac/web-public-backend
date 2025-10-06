package com.elsalvador.coopac.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Proporciona respuestas consistentes para diferentes tipos de errores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja excepciones de validación de argumentos de métodos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        ex.getBindingResult().getGlobalErrors().forEach(error ->
            errors.put(error.getObjectName(), error.getDefaultMessage())
        );

        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message("Los datos proporcionados no son válidos")
            .timestamp(ZonedDateTime.now())
            .details(errors)
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja violaciones de restricciones de validación.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                ConstraintViolation::getMessage
            ));

        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Constraint Violation")
            .message("Los datos no cumplen con las restricciones definidas")
            .timestamp(ZonedDateTime.now())
            .details(errors)
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja excepciones de argumentos ilegales personalizadas.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .message(ex.getMessage())
            .timestamp(ZonedDateTime.now())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja errores de tipo de argumento de método incorrecto.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var requiredTypeName = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido";
        var message = "El parámetro '" + ex.getName() + "' debe ser de tipo " + requiredTypeName;

        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Type Mismatch")
            .message(message)
            .timestamp(ZonedDateTime.now())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja errores de mensaje HTTP no legible (JSON malformado).
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Malformed JSON")
            .message("El formato del JSON no es válido")
            .timestamp(ZonedDateTime.now())
            .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Maneja métodos HTTP no soportados.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        var supportedMethods = ex.getSupportedMethods();
        var methodsString = supportedMethods != null ? String.join(", ", supportedMethods) : "ninguno";
        var message = "El método HTTP '" + ex.getMethod() + "' no está soportado para esta ruta. " +
                     "Métodos soportados: " + methodsString;

        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.METHOD_NOT_ALLOWED.value())
            .error("Method Not Allowed")
            .message(message)
            .timestamp(ZonedDateTime.now())
            .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     * Maneja tipos de media no soportados.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        var message = "El tipo de contenido '" + ex.getContentType() + "' no está soportado. " +
                     "Tipos soportados: " + ex.getSupportedMediaTypes().stream()
                     .map(Object::toString)
                     .collect(Collectors.joining(", "));

        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
            .error("Unsupported Media Type")
            .message(message)
            .timestamp(ZonedDateTime.now())
            .build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorResponse);
    }

    /**
     * Maneja recursos no encontrados.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFoundException(NoResourceFoundException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    /**
     * Maneja rutas no encontradas.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Void> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        var message = "No se encontró un manejador para " + ex.getHttpMethod() + " " + ex.getRequestURL();
        logger.warn("Handler not found: {}", message);
        return ResponseEntity.notFound().build();
    }

    /**
     * Maneja excepciones personalizadas de validación de negocio.
     */
    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessValidationException(BusinessValidationException ex) {
        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .error("Business Validation Error")
            .message(ex.getMessage())
            .timestamp(ZonedDateTime.now());

        // Agregar detalles específicos si están disponibles
        if (ex.getField() != null) {
            errorResponse.addDetail("field", ex.getField());
        }
        if (ex.getRejectedValue() != null) {
            errorResponse.addDetail("rejectedValue", ex.getRejectedValue());
        }

        return ResponseEntity.unprocessableEntity().body(errorResponse.build());
    }

    /**
     * Maneja excepciones personalizadas de recurso no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    /**
     * Maneja todas las excepciones no manejadas específicamente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        var errorResponse = ErrorResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message("Ha ocurrido un error interno del servidor")
            .timestamp(ZonedDateTime.now())
            .build();

        // Log del error para debugging con logger apropiado
        logger.error("Error no manejado: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * DTO para respuestas de error consistentes.
     */
    public record ErrorResponse(
        int status,
        String error,
        String message,
        ZonedDateTime timestamp,
        Map<String, Object> details
    ) {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private int status;
            private String error;
            private String message;
            private ZonedDateTime timestamp;
            private final Map<String, Object> details = new HashMap<>();

            public Builder status(int status) {
                this.status = status;
                return this;
            }

            public Builder error(String error) {
                this.error = error;
                return this;
            }

            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public Builder timestamp(ZonedDateTime timestamp) {
                this.timestamp = timestamp;
                return this;
            }

            public Builder details(Map<String, ?> details) {
                if (details != null) {
                    this.details.putAll(details);
                }
                return this;
            }

            public Builder addDetail(String key, Object value) {
                this.details.put(key, value);
                return this;
            }

            public ErrorResponse build() {
                return new ErrorResponse(status, error, message, timestamp, details);
            }
        }
    }
}
