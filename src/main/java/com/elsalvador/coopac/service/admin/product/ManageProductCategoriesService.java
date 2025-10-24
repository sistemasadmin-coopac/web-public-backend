package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductCategoriesAdminDTO;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar categorías de productos
 */
public interface ManageProductCategoriesService {

    /**
     * Obtiene todas las categorías ordenadas por display_order
     * @return lista de categorías
     */
    List<ProductCategoriesAdminDTO.ProductCategoryListDTO> getAllCategories();

    /**
     * Obtiene una categoría por ID
     * @param categoryId ID de la categoría
     * @return categoría encontrada
     */
    ProductCategoriesAdminDTO.ProductCategoryResponseDTO getCategoryById(UUID categoryId);

    /**
     * Crea una nueva categoría
     * @param createDTO datos de la categoría
     * @return categoría creada
     */
    ProductCategoriesAdminDTO.ProductCategoryResponseDTO createCategory(ProductCategoriesAdminDTO.CreateProductCategoryDTO createDTO);

    /**
     * Actualiza una categoría existente
     * @param categoryId ID de la categoría
     * @param updateDTO datos de actualización
     * @return categoría actualizada
     */
    ProductCategoriesAdminDTO.ProductCategoryResponseDTO updateCategory(UUID categoryId, ProductCategoriesAdminDTO.UpdateProductCategoryDTO updateDTO);

    /**
     * Elimina una categoría
     * Valida que no tenga productos asociados antes de eliminar
     * @param categoryId ID de la categoría
     */
    void deleteCategory(UUID categoryId);

    /**
     * Alterna el estado activo/inactivo de una categoría
     * @param categoryId ID de la categoría
     * @return categoría con estado actualizado
     */
    ProductCategoriesAdminDTO.ProductCategoryResponseDTO toggleActive(UUID categoryId);
}
