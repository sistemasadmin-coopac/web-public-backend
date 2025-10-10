package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderCardsAdminDTO;
import com.elsalvador.coopac.service.admin.header.UpdatePageHeaderCardsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UpdatePageHeaderCardsController {

    private final UpdatePageHeaderCardsService updatePageHeaderCardsService;

    @PutMapping("/page-header-cards")
    public ResponseEntity<PageHeaderCardsAdminDTO> updatePageHeaderCards(@Valid @RequestBody PageHeaderCardsAdminDTO pageHeaderCardsDTO) {
        PageHeaderCardsAdminDTO updatedCard = updatePageHeaderCardsService.updatePageHeaderCards(pageHeaderCardsDTO);
        return ResponseEntity.ok(updatedCard);
    }
}
