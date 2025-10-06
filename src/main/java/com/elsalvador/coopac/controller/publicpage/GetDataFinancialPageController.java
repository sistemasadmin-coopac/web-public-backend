package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.financial.FinancialPageDTO;
import com.elsalvador.coopac.service.publicpages.GetDataFinancialPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financials/page")
@RequiredArgsConstructor
@Slf4j
public class GetDataFinancialPageController {

    private final GetDataFinancialPageService getDataFinancialPageService;

    @GetMapping
    public FinancialPageDTO getFinancial() {
        log.info("Solicitada página Financial");
        return getDataFinancialPageService.getFinancialPageData();
    }
}
