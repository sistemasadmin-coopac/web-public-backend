package com.elsalvador.coopac.controller.admin.join;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.admin.JoinAdminDTO;
import com.elsalvador.coopac.service.admin.join.ManageJoinSpecialBenefitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/join/special-benefits")
@RequiredArgsConstructor
@Tag(name = SwaggerTags.Join.TAG_NAME, description = SwaggerTags.Join.TAG_DESCRIPTION)
public class JoinSpecialBenefitController {

    private final ManageJoinSpecialBenefitService managementService;

    @GetMapping
    @Operation(summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Obtener todos", description = "Obtiene la lista de beneficios especiales")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "500", description = "Error")})
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitListDTO> getAllSpecialBenefits() {
        return ResponseEntity.ok(managementService.getAllSpecialBenefits());
    }

    @PostMapping
    @Operation(summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Crear", description = "Crea un nuevo beneficio especial")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Creado"), @ApiResponse(responseCode = "400", description = "Datos inválidos")})
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitDTO> createSpecialBenefit(@Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(managementService.createSpecialBenefit(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Obtener por ID", description = "Obtiene un beneficio especial")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitDTO> getSpecialBenefitById(@PathVariable @Parameter(description = "ID") UUID id) {
        return ResponseEntity.ok(managementService.getSpecialBenefitById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Actualizar", description = "Actualiza un beneficio especial")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Actualizado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<JoinAdminDTO.JoinSpecialBenefitDTO> updateSpecialBenefit(@PathVariable UUID id, @Valid @RequestBody JoinAdminDTO.CreateUpdateJoinSpecialBenefitDTO dto) {
        return ResponseEntity.ok(managementService.updateSpecialBenefit(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = SwaggerTags.Join.EMOJI_SPECIAL_BENEFITS + " Eliminar", description = "Elimina un beneficio especial")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Eliminado"), @ApiResponse(responseCode = "404", description = "No encontrado")})
    public ResponseEntity<Void> deleteSpecialBenefit(@PathVariable @Parameter(description = "ID") UUID id) {
        managementService.deleteSpecialBenefit(id);
        return ResponseEntity.noContent().build();
    }
}
