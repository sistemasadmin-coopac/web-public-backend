package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.CacheConfig;
import com.elsalvador.coopac.dto.publicpage.join.JoinPageDTO;
import com.elsalvador.coopac.service.publicpages.GetDataJoinPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/join")
@RequiredArgsConstructor
@Slf4j
public class GetDataJoinPageController {

    private final GetDataJoinPageService getDataJoinPageService;

    @GetMapping("/page")
    @Cacheable(value = CacheConfig.JOIN_PAGE_CACHE, unless = "#result == null")
    public JoinPageDTO getJoinPage() {
        log.info("Solicitada página Join/Asóciate Ya");
        return getDataJoinPageService.getJoinPageData();
    }
}

