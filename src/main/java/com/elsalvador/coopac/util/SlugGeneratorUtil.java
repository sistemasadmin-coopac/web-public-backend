package com.elsalvador.coopac.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Utilidad para generar slugs únicos y válidos para URLs
 */
@Component
@Slf4j
public class SlugGeneratorUtil {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

    /**
     * Genera un slug único basado en un texto y una función de verificación de existencia
     * @param text texto base para generar el slug
     * @param existsChecker función que verifica si un slug ya existe
     * @param fallbackPrefix prefijo por defecto si el texto está vacío (default: "item")
     * @return slug único que no existe según el checker
     */
    public String generateUniqueSlug(String text, Function<String, Boolean> existsChecker, String fallbackPrefix) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto no puede estar vacío para generar el slug");
        }

        String baseSlug = createSlugFromText(text, fallbackPrefix);
        String uniqueSlug = baseSlug;
        int counter = 2; // Empezar en 2 para que el primer duplicado sea "slug-2"

        // Verificar si el slug ya existe y agregar número si es necesario
        while (existsChecker.apply(uniqueSlug)) {
            uniqueSlug = baseSlug + "-" + counter;
            counter++;
            log.debug("Slug '{}' ya existe, probando con '{}'", baseSlug, uniqueSlug);
        }

        log.info("Slug único generado: '{}' para texto: '{}'", uniqueSlug, text);
        return uniqueSlug;
    }

    /**
     * Genera un slug único basado en un texto (con prefijo por defecto "item")
     * @param text texto base para generar el slug
     * @param existsChecker función que verifica si un slug ya existe
     * @return slug único que no existe según el checker
     */
    public String generateUniqueSlug(String text, Function<String, Boolean> existsChecker) {
        return generateUniqueSlug(text, existsChecker, "item");
    }

    /**
     * Crea un slug base a partir de un texto (sin verificar unicidad)
     * @param text texto a convertir
     * @param fallbackPrefix prefijo por defecto si el texto resulta vacío
     * @return slug base (sin garantía de unicidad)
     */
    public String createSlugFromText(String text, String fallbackPrefix) {
        if (text == null || text.trim().isEmpty()) {
            return fallbackPrefix != null ? fallbackPrefix : "item";
        }

        // Convertir a minúsculas y normalizar caracteres especiales
        String nowhitespace = WHITESPACE.matcher(text.toLowerCase()).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");

        // Asegurar que no esté vacío
        if (slug.isEmpty()) {
            slug = fallbackPrefix != null ? fallbackPrefix : "item";
        }

        return slug;
    }

    /**
     * Crea un slug base a partir de un texto (con prefijo por defecto "item")
     * @param text texto a convertir
     * @return slug base (sin garantía de unicidad)
     */
    public String createSlugFromText(String text) {
        return createSlugFromText(text, "item");
    }

    /**
     * Valida si un slug tiene un formato válido
     * @param slug slug a validar
     * @return true si es válido, false si no
     */
    public boolean isValidSlugFormat(String slug) {
        if (slug == null || slug.trim().isEmpty()) {
            return false;
        }

        // Validar formato: solo letras minúsculas, números y guiones
        return slug.matches("^[a-z0-9-]+$") && !slug.startsWith("-") && !slug.endsWith("-");
    }

    /**
     * Limpia y valida un slug manualmente proporcionado
     * @param proposedSlug slug propuesto
     * @return slug limpio y válido, o null si no se puede limpiar
     */
    public String cleanAndValidateSlug(String proposedSlug) {
        if (proposedSlug == null || proposedSlug.trim().isEmpty()) {
            return null;
        }

        String cleaned = createSlugFromText(proposedSlug, null);
        return isValidSlugFormat(cleaned) ? cleaned : null;
    }
}
