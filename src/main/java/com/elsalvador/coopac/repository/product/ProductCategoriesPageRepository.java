package com.elsalvador.coopac.repository.product;

import com.elsalvador.coopac.entity.product.ProductCategories;
import com.elsalvador.coopac.projection.product.ProductPageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductCategoriesPageRepository extends JpaRepository<ProductCategories, UUID> {

    /**
     * Obtiene todas las categorías activas ordenadas por display_order
     */
    List<ProductPageView.CategoryView> findByIsActiveTrueOrderByDisplayOrderAsc();
}
