package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.ProductActions;
import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductActionsRepository;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.admin.product.ManageProductActionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar acciones de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageProductActionsServiceImpl implements ManageProductActionsService {

    private final ProductActionsRepository actionsRepository;
    private final ProductsRepository productsRepository;

    @Override
    public ProductsAdminDTO.ProductActionDTO addAction(UUID productId, ProductsAdminDTO.CreateProductActionDTO createDTO) {
        log.info("Añadiendo acción al producto: {}", productId);

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        ProductActions action = ProductActions.builder()
                .product(product)
                .label(createDTO.label())
                .actionType(createDTO.actionType())
                .actionValue(createDTO.actionValue())
                .isPrimary(createDTO.isPrimary() != null ? createDTO.isPrimary() : false)
                .displayOrder(createDTO.displayOrder() != null ? createDTO.displayOrder() : 0)
                .build();

        ProductActions savedAction = actionsRepository.save(action);
        log.info("Acción creada con ID: {}", savedAction.getId());

        return mapToDTO(savedAction);
    }

    @Override
    public ProductsAdminDTO.ProductActionDTO updateAction(UUID actionId, ProductsAdminDTO.UpdateProductActionDTO updateDTO) {
        log.info("Actualizando acción con ID: {}", actionId);

        ProductActions action = actionsRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Acción no encontrada con ID: " + actionId));

        if (updateDTO.label() != null) {
            action.setLabel(updateDTO.label());
        }
        if (updateDTO.actionType() != null) {
            action.setActionType(updateDTO.actionType());
        }
        if (updateDTO.actionValue() != null) {
            action.setActionValue(updateDTO.actionValue());
        }
        if (updateDTO.isPrimary() != null) {
            action.setIsPrimary(updateDTO.isPrimary());
        }
        if (updateDTO.displayOrder() != null) {
            action.setDisplayOrder(updateDTO.displayOrder());
        }

        ProductActions updatedAction = actionsRepository.save(action);
        log.info("Acción actualizada exitosamente");

        return mapToDTO(updatedAction);
    }

    @Override
    public void deleteAction(UUID actionId) {
        log.info("Eliminando acción con ID: {}", actionId);

        if (!actionsRepository.existsById(actionId)) {
            throw new ResourceNotFoundException("Acción no encontrada con ID: " + actionId);
        }

        actionsRepository.deleteById(actionId);
        log.info("Acción eliminada exitosamente");
    }

    /**
     * Mapea entidad a DTO
     */
    private ProductsAdminDTO.ProductActionDTO mapToDTO(ProductActions action) {
        return new ProductsAdminDTO.ProductActionDTO(
                action.getId(),
                action.getLabel(),
                action.getActionType(),
                action.getActionValue(),
                action.getIsPrimary(),
                action.getDisplayOrder()
        );
    }
}
