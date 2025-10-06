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

        ProductSteps step = ProductSteps.builder()
                .product(product)
                .title(createDTO.title())
                .description(createDTO.description())
                .icon(createDTO.icon())
                .estimatedTime(createDTO.estimatedTime())
                .displayOrder(createDTO.displayOrder() != null ? createDTO.displayOrder() : 0)
                .isActive(true)
                .build();

        ProductSteps savedStep = stepsRepository.save(step);
        log.info("Paso creado con ID: {}", savedStep.getId());

        return mapToDTO(savedStep);
    }

    @Override
    public ProductsAdminDTO.ProductStepDTO updateStep(UUID stepId, ProductsAdminDTO.UpdateProductStepDTO updateDTO) {
        log.info("Actualizando paso con ID: {}", stepId);

        ProductSteps step = stepsRepository.findById(stepId)
                .orElseThrow(() -> new ResourceNotFoundException("Paso no encontrado con ID: " + stepId));

        if (updateDTO.title() != null) {
            step.setTitle(updateDTO.title());
        }
        if (updateDTO.description() != null) {
            step.setDescription(updateDTO.description());
        }
        if (updateDTO.icon() != null) {
            step.setIcon(updateDTO.icon());
        }
        if (updateDTO.estimatedTime() != null) {
            step.setEstimatedTime(updateDTO.estimatedTime());
        }
        if (updateDTO.displayOrder() != null) {
            step.setDisplayOrder(updateDTO.displayOrder());
        }
        if (updateDTO.isActive() != null) {
            step.setIsActive(updateDTO.isActive());
        }

        ProductSteps updatedStep = stepsRepository.save(step);
        log.info("Paso actualizado exitosamente");

        return mapToDTO(updatedStep);
    }

    @Override
    public void deleteStep(UUID stepId) {
        log.info("Eliminando paso con ID: {}", stepId);

        if (!stepsRepository.existsById(stepId)) {
            throw new ResourceNotFoundException("Paso no encontrado con ID: " + stepId);
        }

        stepsRepository.deleteById(stepId);
        log.info("Paso eliminado exitosamente");
    }

    @Override
    public ProductsAdminDTO.ProductStepDTO toggleStepStatus(UUID stepId, Boolean isActive) {
        log.info("Cambiando estado del paso {} a: {}", stepId, isActive);

        ProductSteps step = stepsRepository.findById(stepId)
                .orElseThrow(() -> new ResourceNotFoundException("Paso no encontrado con ID: " + stepId));

        step.setIsActive(isActive);
        ProductSteps updatedStep = stepsRepository.save(step);

        log.info("Estado del paso {} actualizado a: {}", stepId, isActive);

        return mapToDTO(updatedStep);
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
