package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.service.admin.header.GetPageHeaderCardsByHeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class GetPageHeaderCardsByHeaderController {

    private final GetPageHeaderCardsByHeaderService getPageHeaderCardsByHeaderService;

    @GetMapping("/page-header-cards/header/{headerId}")
    public ResponseEntity<List<PageHeaderCardsAdminDTO>> getPageHeaderCardsByHeader(@PathVariable String headerId) {
        List<PageHeaderCardsAdminDTO> cards = getPageHeaderCardsByHeaderService.getPageHeaderCardsByHeader(headerId);
        return ResponseEntity.ok(cards);
    }
}
