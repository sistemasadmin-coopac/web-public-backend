package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.about.AboutPageDTO;
import com.elsalvador.coopac.service.publicpages.GetDataAboutPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
@Slf4j
public class GetDataAboutPageController {

    private final GetDataAboutPageService getDataAboutPageServiceImpl;

    @GetMapping("/page")
    public AboutPageDTO getAboutPage() {
        log.info("Solicitada página About");
        return getDataAboutPageServiceImpl.getAboutPageData();
    }
}
