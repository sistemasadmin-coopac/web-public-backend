package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.service.admin.header.GetPageHeaderBySlugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class GetPageHeaderBySlugController {

    private final GetPageHeaderBySlugService getPageHeaderBySlugService;

    @GetMapping("/page-headers/slug/{pageSlug}")
    public ResponseEntity<PageHeaderAdminDTO> getPageHeaderBySlug(@PathVariable String pageSlug) {
        PageHeaderAdminDTO header = getPageHeaderBySlugService.getPageHeaderBySlug(pageSlug);
        return ResponseEntity.ok(header);
    }
}
