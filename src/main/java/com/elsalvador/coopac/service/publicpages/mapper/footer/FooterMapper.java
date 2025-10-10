package com.elsalvador.coopac.service.publicpages.mapper.footer;

import com.elsalvador.coopac.dto.publicpage.footer.FooterDTO;
import com.elsalvador.coopac.entity.navigation.FooterColumns;
import com.elsalvador.coopac.entity.navigation.FooterLinks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de Footer a DTOs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FooterMapper {

    /**
     * Mapea el footer completo con columnas y información del sitio
     */
    public FooterDTO mapFooter(List<FooterColumns> columns, String companyName, String logoUrl,
                              String logoAlt, String phone, String email, String address,
                              String facebook, String instagram, String linkedin, String twitter) {

        var columnDtos = columns.stream()
                .map(this::mapFooterColumn)
                .collect(Collectors.toList());

        var contactInfo = new FooterDTO.ContactInfoDTO(phone, email, address);
        var socialLinks = new FooterDTO.SocialLinksDTO(facebook, instagram, linkedin, twitter);

        var footerInfo = new FooterDTO.FooterInfoDTO(
                companyName,
                logoUrl,
                logoAlt,
                contactInfo,
                socialLinks,
                "© " + java.time.Year.now().getValue() + " " + companyName + ". Todos los derechos reservados."
        );

        return new FooterDTO(columnDtos, footerInfo);
    }

    /**
     * Mapea una columna del footer individual
     */
    private FooterDTO.FooterColumnDTO mapFooterColumn(FooterColumns column) {
        var linkDtos = column.getLinks() != null ?
                column.getLinks().stream()
                    .filter(FooterLinks::getIsActive)
                    .map(this::mapFooterLink)
                    .collect(Collectors.toList()) :
                List.<FooterDTO.FooterLinkDTO>of();

        return new FooterDTO.FooterColumnDTO(
                column.getTitle(),
                linkDtos,
                column.getDisplayOrder()
        );
    }

    /**
     * Mapea un link del footer individual
     */
    private FooterDTO.FooterLinkDTO mapFooterLink(FooterLinks link) {
        return new FooterDTO.FooterLinkDTO(
                link.getLabel(),
                link.getUrl(),
                link.getExternal(),
                link.getDisplayOrder()
        );
    }
}
