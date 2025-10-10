package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.navigation.NavigationDTO;
import com.elsalvador.coopac.service.publicpages.GetDataNavigationPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/navigation")
@RequiredArgsConstructor
@Slf4j
public class GetDataNavigationPageController {

    private final GetDataNavigationPageService getDataNavigationPageService;

    @GetMapping("/main")
    public NavigationDTO getMainNavigation() {
        log.info("Solicitada navegación principal");
        return getDataNavigationPageService.getMainNavigation();
    }
}
