package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.service.publicpages.GetDataHomePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GetDataHomePageController {

    private final GetDataHomePageService getDataHomePageService;

    @GetMapping("/home")
    public HomePageDTO getHomePage() {
        return getDataHomePageService.getHomePageData();
    }
}
