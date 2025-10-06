package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.footer.FooterDTO;
import com.elsalvador.coopac.service.publicpages.GetDataFooterPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/footer")
@RequiredArgsConstructor
@Slf4j
public class GetDataFooterPageController {

    private final GetDataFooterPageService getDataFooterPageService;

    @GetMapping
    public FooterDTO getFooter() {
        log.info("Solicitados datos del footer");
        return getDataFooterPageService.getFooterData();
    }
}
