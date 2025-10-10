package com.elsalvador.coopac.controller.admin.header;

import com.elsalvador.coopac.dto.admin.PageHeaderAdminDTO;
import com.elsalvador.coopac.service.admin.header.UpdatePageHeaderService;
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
public class UpdatePageHeaderController {

    private final UpdatePageHeaderService updatePageHeaderService;

    @PutMapping("/page-headers")
    public ResponseEntity<PageHeaderAdminDTO> updatePageHeader(@Valid @RequestBody PageHeaderAdminDTO pageHeaderDTO) {
        PageHeaderAdminDTO updatedHeader = updatePageHeaderService.updatePageHeader(pageHeaderDTO);
        return ResponseEntity.ok(updatedHeader);
    }
}
