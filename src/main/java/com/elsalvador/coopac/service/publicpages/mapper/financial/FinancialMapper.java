package com.elsalvador.coopac.service.publicpages.mapper.financial;

import com.elsalvador.coopac.dto.publicpage.financial.FinancialPageDTO;
import com.elsalvador.coopac.entity.financial.FinancialReportCategories;
import com.elsalvador.coopac.entity.financial.FinancialReports;
import com.elsalvador.coopac.entity.financial.FinancialsIntro;
import com.elsalvador.coopac.entity.page.PageHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de Financial a DTOs
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FinancialMapper {

    /**
     * Mapea el header de Financial usando PageHeaders
     */
    public FinancialPageDTO.HeaderDTO mapHeader(PageHeaders pageHeader) {
        return new FinancialPageDTO.HeaderDTO(
                pageHeader.getTitleMain(),
                pageHeader.getSubtitle()
        );
    }

    /**
     * Mapea la introducción financiera
     */
    public FinancialPageDTO.IntroDTO mapIntro(FinancialsIntro intro) {
        return new FinancialPageDTO.IntroDTO(
                intro.getIntroText()
        );
    }

    /**
     * Mapea una categoría de reportes con sus reportes
     */
    public FinancialPageDTO.CategoryDTO mapCategory(FinancialReportCategories category, List<FinancialReports> reports, Long reportCount) {
        var reportDtos = reports.stream()
                .map(this::mapReport)
                .collect(Collectors.toList());

        return new FinancialPageDTO.CategoryDTO(
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                reportCount != null ? reportCount.intValue() : reportDtos.size(),
                reportDtos
        );
    }

    /**
     * Mapea un reporte individual
     */
    private FinancialPageDTO.ReportDTO mapReport(FinancialReports report) {
        var file = new FinancialPageDTO.FileDTO(
                report.getFileFormat(),
                report.getFileUrl(),
                report.getFileSizeBytes()
        );

        List<String> tags = report.getTags() != null ?
                Arrays.asList(report.getTags()) :
                List.of();

        return new FinancialPageDTO.ReportDTO(
                report.getId(),
                report.getSlug(),
                report.getTitle(),
                report.getSummary(),
                report.getPublishDate(),
                file,
                report.getThumbnailUrl(),
                tags,
                report.getIsPublic(),
                report.getDisplayOrder()
        );
    }
}
