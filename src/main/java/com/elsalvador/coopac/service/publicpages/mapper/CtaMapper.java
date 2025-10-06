package com.elsalvador.coopac.service.publicpages.mapper;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.entity.home.HomeCtaBlocks;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de CTAs a DTOs
 */
@Component
public class CtaMapper {

    /**
     * Mapea bloques de CTA a CtasSectionDTO
     */
    public HomePageDTO.CtasSectionDTO toCtasSectionDTO(List<HomeCtaBlocks> midCtas, List<HomeCtaBlocks> finalCtas) {
        return new HomePageDTO.CtasSectionDTO(
            mapCtas(midCtas),
            mapCtas(finalCtas)
        );
    }

    /**
     * Mapea los bloques de CTA individuales
     */
    private List<HomePageDTO.CtaDTO> mapCtas(List<HomeCtaBlocks> ctas) {
        return ctas.stream()
            .map(cta -> {
                var button = new HomePageDTO.CtaLinkDTO(
                    cta.getButtonText(),
                    cta.getButtonUrl()
                );

                return new HomePageDTO.CtaDTO(
                    cta.getTitle(),
                    cta.getSubtitle(),
                    button,
                    cta.getDisplayOrder()
                );
            })
            .collect(Collectors.toList());
    }
}
