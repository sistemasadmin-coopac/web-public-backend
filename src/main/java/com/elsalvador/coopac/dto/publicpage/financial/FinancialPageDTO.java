package com.elsalvador.coopac.dto.publicpage.financial;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FinancialPageDTO(
    HeaderDTO header,
    SectionsDTO sections
) {

    public record HeaderDTO(
        String titleMain,
        String subtitle
    ) {}

    public record SectionsDTO(
        IntroDTO intro,
        List<CategoryDTO> categories
    ) {}

    public record IntroDTO(
        String text
    ) {}

    public record CategoryDTO(
        String name,
        String slug,
        String description,
        Integer count,
        List<ReportDTO> items
    ) {}

    public record ReportDTO(
        UUID id,
        String slug,
        String title,
        String summary,
        LocalDate publishDate,
        FileDTO file,
        String thumbnailUrl,
        List<String> tags,
        Boolean isPublic,
        Integer order
    ) {}

    public record FileDTO(
        String format,
        String url,
        Long sizeBytes
    ) {}
}
