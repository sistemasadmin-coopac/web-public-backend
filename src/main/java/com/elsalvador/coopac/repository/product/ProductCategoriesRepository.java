package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, UUID> {

    /**
     * Encuentra todas las categorías activas ordenadas por display_order
     */
    @Query("""
        select pc
        from ProductCategories pc
        where pc.isActive = true
        order by pc.displayOrder asc
        """)
    List<ProductCategories> findAllActiveOrderByDisplayOrder();

    /**
     * Encuentra todas las categorías ordenadas por display_order (incluye inactivas para admin)
     */
    @Query("""
        select pc
        from ProductCategories pc
        order by pc.displayOrder asc
        """)
    List<ProductCategories> findAllOrderByDisplayOrder();

    /**
     * Busca una categoría por slug
     */
    Optional<ProductCategories> findBySlug(String slug);

    /**
     * Verifica si existe una categoría con el slug especificado (excluyendo la categoría actual)
     */
    @Query("""
        select count(pc) > 0
        from ProductCategories pc
        where pc.slug = :slug and pc.id != :excludeId
        """)
    boolean existsBySlugAndIdNot(@Param("slug") String slug, @Param("excludeId") UUID excludeId);

    /**
     * Verifica si existe una categoría con el slug especificado
     */
    boolean existsBySlug(String slug);

    /**
     * Verifica si existe una categoría con el nombre especificado
     */
    boolean existsByName(String name);

    /**
     * Verifica si existe una categoría con el nombre especificado (excluyendo la categoría actual)
     */
    @Query("""
        select count(pc) > 0
        from ProductCategories pc
        where pc.name = :name and pc.id != :excludeId
        """)
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("excludeId") UUID excludeId);

    /**
     * Cuenta cuántos productos están asociados a una categoría
     */
    @Query("""
        select count(p)
        from Products p
        where p.category.id = :categoryId
        """)
    long countProductsByCategoryId(@Param("categoryId") UUID categoryId);

    /**
     * Verifica si una categoría tiene productos asociados
     */
    @Query("""
        select count(p) > 0
        from Products p
        where p.category.id = :categoryId
        """)
    boolean hasAssociatedProducts(@Param("categoryId") UUID categoryId);
}
