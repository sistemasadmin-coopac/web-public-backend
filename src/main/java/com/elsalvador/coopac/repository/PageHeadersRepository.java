package com.elsalvador.coopac.repository;

import com.elsalvador.coopac.entity.page.PageHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para los headers de páginas
 */
@Repository
public interface PageHeadersRepository extends JpaRepository<PageHeaders, UUID> {

    /**
     * Encuentra el header activo para una página específica ordenado por display_order
     * @param pageSlug enum de la página (ej: PageSlug.HOME)
     * @return header activo o empty
     */
    Optional<PageHeaders> findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(String pageSlug);

    /**
     * Encuentra todos los headers para una página específica por slug
     * @param pageSlug slug de la página
     * @return lista de headers para la página
     */
    List<PageHeaders> findByPageSlugOrderByDisplayOrderAsc(String pageSlug);

    /**
     * Retorna un header por su ID
     *
     * @param id
     * @return
     */
    Optional<PageHeaders> findById(UUID id);


    Optional<PageHeaders> findByPageSlug(String pageSlug);
}
