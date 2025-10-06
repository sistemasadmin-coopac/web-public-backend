package com.elsalvador.coopac.dto.publicpage.footer;

import java.util.List;

public record FooterDTO(
    List<FooterColumnDTO> columns,
    FooterInfoDTO info
) {

    public record FooterColumnDTO(
        String title,
        List<FooterLinkDTO> links,
        Integer order
    ) {}

    public record FooterLinkDTO(
        String label,
        String url,
        Boolean external,
        Integer order
    ) {}

    public record FooterInfoDTO(
        String companyName,
        String logoUrl,
        String logoAlt,
        ContactInfoDTO contact,
        SocialLinksDTO social,
        String copyrightText
    ) {}

    public record ContactInfoDTO(
        String phone,
        String email,
        String address
    ) {}

    public record SocialLinksDTO(
        String facebook,
        String instagram,
        String linkedin,
        String twitter
    ) {}
}
