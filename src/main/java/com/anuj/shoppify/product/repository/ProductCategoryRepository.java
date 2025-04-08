package com.anuj.shoppify.product.repository;

import com.anuj.shoppify.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
     Page<ProductCategory> findAll(Pageable pageable);

}
