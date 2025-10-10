package com.elsalvador.coopac.service.publicpages.mapper;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.entity.home.HomeStats;
import com.elsalvador.coopac.entity.home.HomeStatsSection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de estadísticas a DTOs
 */
@Component
public class StatsMapper {

    /**
     * Mapea una sección de estadísticas a StatsSectionDTO
     */
    public HomePageDTO.StatsSectionDTO toStatsSectionDTO(HomeStatsSection statsSection, List<HomeStats> stats) {
        if (statsSection == null) {
            return null;
        }

        return new HomePageDTO.StatsSectionDTO(
            statsSection.getTitle(),
            statsSection.getSubtitle(),
            mapStats(stats)
        );
    }

    /**
     * Mapea las estadísticas individuales
     */
    private List<HomePageDTO.StatDTO> mapStats(List<HomeStats> stats) {
        return stats.stream()
            .map(stat -> new HomePageDTO.StatDTO(
                stat.getLabel(),
                stat.getValueText(),
                stat.getIcon(),
                stat.getDisplayOrder()
            ))
            .collect(Collectors.toList());
    }
}
