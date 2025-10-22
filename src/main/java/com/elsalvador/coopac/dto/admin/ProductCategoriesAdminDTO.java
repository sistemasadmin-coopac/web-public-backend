package com.elsalvador.coopac.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTOs para administración de categorías de productos
 */
public class ProductCategoriesAdminDTO {

    /**
     * DTO para respuesta completa de categoría
     */
    public record ProductCategoryResponseDTO(
            UUID id,
            String name,
            String slug,
            String description,
            String icon,
            Integer displayOrder,
            Boolean isActive,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long productsCount
    ) {}

    /**
     * DTO para crear categoría
     */
    public record CreateProductCategoryDTO(
            @NotBlank(message = "El nombre es obligatorio")
            @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
            String name,

            @NotBlank(message = "El slug es obligatorio")
            @Size(max = 100, message = "El slug no puede tener más de 100 caracteres")
            String slug,

            @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
            String description,

            @Size(max = 50, message = "El icono no puede tener más de 50 caracteres")
            String icon,

            @NotNull(message = "El orden de visualización es obligatorio")
            Integer displayOrder,

            @NotNull(message = "El estado activo es obligatorio")
            Boolean isActive
    ) {}

    /**
     * DTO para actualizar categoría
     */
    public record UpdateProductCategoryDTO(
            @NotBlank(message = "El nombre es obligatorio")
            @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
            String name,

            @NotBlank(message = "El slug es obligatorio")
            @Size(max = 100, message = "El slug no puede tener más de 100 caracteres")
            String slug,

            @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
            String description,

            @Size(max = 50, message = "El icono no puede tener más de 50 caracteres")
            String icon,

            @NotNull(message = "El orden de visualización es obligatorio")
            Integer displayOrder,

            @NotNull(message = "El estado activo es obligatorio")
            Boolean isActive
    ) {}

    /**
     * DTO simplificado para listas
     */
    public record ProductCategoryListDTO(
            UUID id,
            String name,
            String slug,
            String icon,
            Integer displayOrder,
            Boolean isActive,
            Long productsCount
    ) {}

    /**
     * DTO para crear categoría (request del frontend - sin slug)
     */
    public record CreateProductCategoryRequestDTO(
            @NotBlank(message = "El nombre es obligatorio")
            @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
            String name,

            @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
            String description,

            @Size(max = 50, message = "El icono no puede tener más de 50 caracteres")
            String icon,

            Integer displayOrder,

            @NotNull(message = "El estado activo es obligatorio")
            Boolean isActive
    ) {}

    /**
     * DTO para actualizar categoría (request del frontend - sin slug)
     */
    public record UpdateProductCategoryRequestDTO(
            @NotBlank(message = "El nombre es obligatorio")
            @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
            String name,

            @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
            String description,

            @Size(max = 50, message = "El icono no puede tener más de 50 caracteres")
            String icon,

            Integer displayOrder,

            @NotNull(message = "El estado activo es obligatorio")
            Boolean isActive
    ) {}
}
