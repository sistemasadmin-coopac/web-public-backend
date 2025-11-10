package com.elsalvador.coopac.controller.publicpage;

import com.elsalvador.coopac.config.SwaggerTags;
import com.elsalvador.coopac.dto.publicpage.product.ProductDetailDTO;
import com.elsalvador.coopac.dto.publicpage.product.ProductPageDTO;
import com.elsalvador.coopac.dto.response.ErrorResponseDTO;
import com.elsalvador.coopac.service.publicpages.GetDataProductDetailPageService;
import com.elsalvador.coopac.service.publicpages.GetDataProductPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = SwaggerTags.PublicPages.TAG_NAME, description = SwaggerTags.PublicPages.TAG_DESCRIPTION)
public class GetDataProductPageController {

    private final GetDataProductPageService getDataProductPageService;
    private final GetDataProductDetailPageService getDataProductDetailPageService;

    @GetMapping("/page")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_PRODUCTS + " Obtener datos de pagina Products",
        description = "Obtiene la informacion completa de la pagina de productos incluyendo categorias, listado de productos y filtros"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Datos de la pagina Products obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ProductPageDTO getProductsPage() {
        return getDataProductPageService.getProductsPageData();
    }

    @GetMapping("/{slug}")
    @Operation(
        summary = SwaggerTags.PublicPages.EMOJI_PRODUCTS + " Obtener detalle de producto",
        description = "Obtiene la informacion detallada de un producto especifico incluyendo caracteristicas, acciones, pasos e informacion financiera"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles del producto obtenidos exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ProductDetailDTO getProductDetail(
            @PathVariable
            @Parameter(description = "Slug unico del producto", example = "ahorros-programados")
            String slug) {
        return getDataProductDetailPageService.getProductDetail(slug);
    }
}


