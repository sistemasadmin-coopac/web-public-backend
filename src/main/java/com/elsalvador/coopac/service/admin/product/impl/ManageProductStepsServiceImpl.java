package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.ProductSteps;
import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductStepsRepository;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.admin.product.ManageProductStepsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementación del servicio para gestionar pasos de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageProductStepsServiceImpl implements ManageProductStepsService {

    private final ProductStepsRepository stepsRepository;
    private final ProductsRepository productsRepository;

    @Override
    public ProductsAdminDTO.ProductStepDTO addStep(UUID productId, ProductsAdminDTO.CreateProductStepDTO createDTO) {
        log.info("Añadiendo paso al producto: {}", productId);

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        // Obtener el siguiente número de orden automáticamente
        Integer nextOrder = stepsRepository.findMaxDisplayOrderByProductId(productId) + 1;

        // Generar título automáticamente: "Paso 1", "Paso 2", etc.
        String autoTitle = "Paso " + nextOrder;

        log.info("Generando paso automático: '{}' con displayOrder: {}", autoTitle, nextOrder);

        ProductSteps step = ProductSteps.builder()
                .product(product)
                .title(autoTitle)
                .description(createDTO.description())
                .icon(createDTO.icon())
                .estimatedTime(createDTO.estimatedTime())
                .displayOrder(nextOrder)
                .isActive(true)
                .build();

        ProductSteps savedStep = stepsRepository.save(step);
        log.info("Paso creado con ID: {}, título: '{}', orden: {}", savedStep.getId(), savedStep.getTitle(), savedStep.getDisplayOrder());

        return mapToDTO(savedStep);
    }

    @Override
    public ProductsAdminDTO.ProductStepDTO updateStep(UUID stepId, ProductsAdminDTO.UpdateProductStepDTO updateDTO) {
        log.info("Actualizando paso con ID: {}", stepId);

        ProductSteps step = stepsRepository.findById(stepId)
                .orElseThrow(() -> new ResourceNotFoundException("Paso no encontrado con ID: " + stepId));

        // Solo actualizar los campos permitidos (NO title ni displayOrder)
        if (updateDTO.description() != null) {
            step.setDescription(updateDTO.description());
        }
        if (updateDTO.icon() != null) {
            step.setIcon(updateDTO.icon());
        }
        if (updateDTO.estimatedTime() != null) {
            step.setEstimatedTime(updateDTO.estimatedTime());
        }
        if (updateDTO.isActive() != null) {
            step.setIsActive(updateDTO.isActive());
        }

        ProductSteps updatedStep = stepsRepository.save(step);
        log.info("Paso actualizado exitosamente (título y orden permanecen sin cambios)");

        return mapToDTO(updatedStep);
    }

    @Override
    public void deleteStep(UUID stepId) {
        log.info("Eliminando paso con ID: {}", stepId);

        ProductSteps stepToDelete = stepsRepository.findById(stepId)
                .orElseThrow(() -> new ResourceNotFoundException("Paso no encontrado con ID: " + stepId));

        UUID productId = stepToDelete.getProduct().getId();
        Integer deletedOrder = stepToDelete.getDisplayOrder();

        // Eliminar el paso
        stepsRepository.deleteById(stepId);
        log.info("Paso eliminado: '{}' (orden {})", stepToDelete.getTitle(), deletedOrder);

        // Reorganizar los pasos restantes para mantener secuencia sin huecos
        reorganizeSteps(productId);
    }

    @Override
    public ProductsAdminDTO.ProductStepDTO toggleStepStatus(UUID stepId, Boolean isActive) {
        log.info("Cambiando estado del paso {} a: {}", stepId, isActive);

        ProductSteps step = stepsRepository.findById(stepId)
                .orElseThrow(() -> new ResourceNotFoundException("Paso no encontrado con ID: " + stepId));

        step.setIsActive(isActive);
        ProductSteps updatedStep = stepsRepository.save(step);

        log.info("Estado del paso '{}' actualizado a: {}", step.getTitle(), isActive);

        return mapToDTO(updatedStep);
    }

    /**
     * Reorganiza los pasos de un producto para mantener la secuencia sin huecos
     * y actualiza los títulos para que sean "Paso 1", "Paso 2", etc.
     */
    private void reorganizeSteps(UUID productId) {
        log.info("Reorganizando pasos del producto: {}", productId);

        // Obtener todos los pasos ordenados
        List<ProductSteps> steps = stepsRepository.findByProductIdOrderByDisplayOrderAsc(productId);

        // Reorganizar con numeración consecutiva
        int newOrder = 1;
        for (ProductSteps step : steps) {
            step.setDisplayOrder(newOrder);
            step.setTitle("Paso " + newOrder);
            stepsRepository.save(step);
            log.info("Paso reorganizado: ID={}, nuevo título='{}', nuevo orden={}",
                    step.getId(), step.getTitle(), newOrder);
            newOrder++;
        }

        log.info("Reorganización completada. Total de pasos: {}", steps.size());
    }

    /**
     * Mapea entidad a DTO
     */
    private ProductsAdminDTO.ProductStepDTO mapToDTO(ProductSteps step) {
        return new ProductsAdminDTO.ProductStepDTO(
                step.getId(),
                step.getTitle(),
                step.getDescription(),
                step.getIcon(),
                step.getEstimatedTime(),
                step.getDisplayOrder(),
                step.getIsActive()
        );
    }
}
