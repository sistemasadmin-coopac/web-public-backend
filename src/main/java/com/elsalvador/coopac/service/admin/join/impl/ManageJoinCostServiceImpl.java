package com.elsalvador.coopac.service.admin.join.impl;

import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.entity.join.JoinCost;
import com.elsalvador.coopac.entity.join.JoinSection;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.join.JoinCostRepository;
import com.elsalvador.coopac.repository.join.JoinSectionRepository;
import com.elsalvador.coopac.service.admin.join.ManageJoinCostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar costos de Join
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManageJoinCostServiceImpl implements ManageJoinCostService {

    private final JoinCostRepository costRepository;
    private final JoinSectionRepository joinSectionRepository;

    @Override
    public JoinAdminDTO.JoinCostListDTO getAllCosts() {
        log.info("Obteniendo todos los costos");

        List<JoinCost> costs = costRepository.findAll().stream()
                .sorted(Comparator.comparing(c -> c.getSectionOrder() != null ? c.getSectionOrder() : 0))
                .collect(Collectors.toList());

        List<JoinAdminDTO.JoinCostDTO> dtos = costs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinCostListDTO(dtos, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinCostDTO getCostById(UUID id) {
        log.info("Obteniendo costo con ID: {}", id);
        JoinCost cost = costRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Costo no encontrado con ID: " + id));
        return mapToDTO(cost);
    }

    @Override
    public JoinAdminDTO.JoinSpecialBenefitRequirementListDTO getCostsBySection(UUID joinSectionId) {
        log.info("Obteniendo costos para la sección con ID: {}", joinSectionId);

        List<JoinCost> costs = costRepository.findByJoinSectionIdOrderBySectionOrderAsc(joinSectionId);

        List<JoinAdminDTO.JoinCostDTO> dtos = costs.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return new JoinAdminDTO.JoinSpecialBenefitRequirementListDTO(null, dtos.size());
    }

    @Override
    public JoinAdminDTO.JoinCostDTO createCost(JoinAdminDTO.CreateUpdateJoinCostDTO dto) {
        log.info("Creando nuevo costo con etiqueta: {}", dto.label());

        // Calcular el siguiente orden
        Integer nextOrder = costRepository.findAll().stream()
                .mapToInt(c -> c.getSectionOrder() != null ? c.getSectionOrder() : 0)
                .max()
                .orElse(0) + 1;

        JoinCost cost = JoinCost.builder()
                .label(dto.label())
                .amount(dto.amount())
                .sectionOrder(nextOrder)
                .build();

        JoinCost saved = costRepository.save(cost);
        log.info("Costo creado con ID: {} - Orden: {}", saved.getId(), nextOrder);

        return mapToDTO(saved);
    }

    @Override
    public JoinAdminDTO.JoinCostDTO updateCost(UUID id, JoinAdminDTO.CreateUpdateJoinCostDTO dto) {
        log.info("Actualizando costo con ID: {}", id);

        JoinCost cost = costRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Costo no encontrado con ID: " + id));

        cost.setLabel(dto.label());
        cost.setAmount(dto.amount());
        // sectionOrder no se modifica, es gestionado por el backend

        JoinCost updated = costRepository.save(cost);
        log.info("Costo actualizado con ID: {}", updated.getId());

        return mapToDTO(updated);
    }

    @Override
    public void deleteCost(UUID id) {
        log.info("Eliminando costo con ID: {}", id);

        JoinCost cost = costRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Costo no encontrado con ID: " + id));

        costRepository.delete(cost);
        log.info("Costo eliminado con ID: {}", id);
    }

    /**
     * Mapea una entidad JoinCost a su DTO
     */
    private JoinAdminDTO.JoinCostDTO mapToDTO(JoinCost cost) {
        return new JoinAdminDTO.JoinCostDTO(
                cost.getId(),
                cost.getLabel(),
                cost.getAmount(),
                cost.getSectionOrder()
        );
    }
}

