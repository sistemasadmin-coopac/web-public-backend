package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.dto.publicpage.contact.ContactPageDTO;
import com.elsalvador.coopac.service.publicpages.GetDataContactPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@Slf4j
public class GetDataContactPageController {

    private final GetDataContactPageService getDataContactPageService;

    @GetMapping("/page")
    public ContactPageDTO getContact() {
        log.info("Solicitada página Contact");
        return getDataContactPageService.getContactPageData();
    }
}
