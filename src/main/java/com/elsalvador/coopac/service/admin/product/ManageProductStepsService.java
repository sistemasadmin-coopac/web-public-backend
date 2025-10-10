package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

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
