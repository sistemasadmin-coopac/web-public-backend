package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.projection.ProductCardView;
import com.elsalvador.coopac.projection.product.ProductDetailView;
import com.elsalvador.coopac.projection.product.ProductPageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {

    /**
     * Encuentra todos los productos activos y destacados con categoría activa ordenados por display_order
     * @return lista de productos destacados para mostrar en el home
     */
    @Query("""
        select p
        from Products p
          join p.category c
        where p.isActive = true
          and p.isFeatured = true
          and c.isActive = true
        order by p.displayOrder asc
        """)
    List<ProductCardView> findFeaturedCards();

    /**
     * Obtiene todos los productos activos para la página de productos
     * Solo productos con categorías activas, ordenados por display_order
     */
    @Query("""
        select p
        from Products p
        join p.category c
        where p.isActive = true
          and c.isActive = true
        order by p.displayOrder asc
        """)
    List<ProductPageView.ProductGridView> findAllActiveForGrid();

    /**
     * Obtiene un producto específico por slug para mostrar el detalle
     */
    @Query("""
        select p
        from Products p
        join p.category c
        where p.slug = :slug
          and p.isActive = true
          and c.isActive = true
        """)
    Optional<ProductDetailView.ProductFullView> findBySlugForDetail(@Param("slug") String slug);

    /**
     * Verifica si existe un producto activo con el slug dado
     */
    boolean existsBySlugAndIsActiveTrue(String slug);

    // Métodos para administración

    /**
     * Obtiene todos los productos activos con categoría para administración
     */
    @Query("""
        select p
        from Products p
        join fetch p.category c
        where p.isActive = true
        order by c.name asc, p.displayOrder asc
        """)
    List<Products> findAllActiveWithCategory();

    /**
     * Obtiene todos los productos (activos e inactivos) para administración
     */
    @Query("""
        select p
        from Products p
        join fetch p.category c
        order by c.name asc, p.displayOrder asc
        """)
    List<Products> findAllWithCategory();

    /**
     * Obtiene productos por categoría para administración
     */
    @Query("""
        select p
        from Products p
        join fetch p.category c
        where c.id = :categoryId
        order by p.displayOrder asc
        """)
    List<Products> findByCategoryIdWithCategory(@Param("categoryId") UUID categoryId);

    /**
     * Obtiene un producto por ID con todas sus relaciones para administración
     * Solucionado: Evita MultipleBagFetchException cargando solo la entidad base primero
     */
    @Query("""
        select p
        from Products p
        left join fetch p.category c
        left join fetch p.financialInfo fi
        where p.id = :id
        """)
    Optional<Products> findByIdWithAllRelations(@Param("id") UUID id);

    /**
     * Carga las features de un producto específico
     */
    @Query("""
        select p
        from Products p
        left join fetch p.features f
        where p.id = :id
        """)
    Optional<Products> findByIdWithFeatures(@Param("id") UUID id);

    /**
     * Carga las actions de un producto específico
     */
    @Query("""
        select p
        from Products p
        left join fetch p.actions a
        where p.id = :id
        """)
    Optional<Products> findByIdWithActions(@Param("id") UUID id);

    /**
     * Carga los badges de un producto específico
     */
    @Query("""
        select p
        from Products p
        left join fetch p.badges b
        where p.id = :id
        """)
    Optional<Products> findByIdWithBadges(@Param("id") UUID id);

    /**
     * Carga los steps de un producto específico
     */
    @Query("""
        select p
        from Products p
        left join fetch p.steps s
        where p.id = :id
        """)
    Optional<Products> findByIdWithSteps(@Param("id") UUID id);

    /**
     * Obtiene un producto por slug con todas sus relaciones para administración
     * Solucionado: Evita MultipleBagFetchException cargando solo la entidad base primero
     */
    @Query("""
        select p
        from Products p
        left join fetch p.category c
        left join fetch p.financialInfo fi
        where p.slug = :slug
        """)
    Optional<Products> findBySlugWithAllRelations(@Param("slug") String slug);

    /**
     * Verifica si existe un slug (para validación en creación/actualización)
     */
    boolean existsBySlug(String slug);

    /**
     * Verifica si existe un slug excluyendo un ID específico (para validación en actualización)
     */
    boolean existsBySlugAndIdNot(String slug, UUID id);
}
