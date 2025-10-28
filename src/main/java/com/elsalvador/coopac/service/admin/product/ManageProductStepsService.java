package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar pasos de productos
 */
public interface ManageProductStepsService {

    /**
     * Añade un paso a un producto
     * @param productId ID del producto
     * @param createDTO datos del paso
     * @return paso creado
     */
    ProductsAdminDTO.ProductStepDTO addStep(UUID productId, ProductsAdminDTO.CreateProductStepDTO createDTO);

    /**
     * Añade múltiples pasos a un producto en una sola operación (batch)
     * @param productId ID del producto
     * @param stepsDTO lista de datos de pasos
     * @return lista de pasos creados con numeración correcta: Paso 1, Paso 2, etc.
     */
    List<ProductsAdminDTO.ProductStepDTO> addMultipleSteps(UUID productId, List<ProductsAdminDTO.CreateProductStepDTO> stepsDTO);

    /**
     * Actualiza un paso
     * @param stepId ID del paso
     * @param updateDTO datos de actualización
     * @return paso actualizado
     */
    ProductsAdminDTO.ProductStepDTO updateStep(UUID stepId, ProductsAdminDTO.UpdateProductStepDTO updateDTO);

    /**
     * Elimina un paso
     * @param stepId ID del paso
     */
    void deleteStep(UUID stepId);

    /**
     * Activa o desactiva un paso
     * @param stepId ID del paso
     * @param isActive estado activo/inactivo
     * @return paso actualizado
     */
    ProductsAdminDTO.ProductStepDTO toggleStepStatus(UUID stepId, Boolean isActive);
}
