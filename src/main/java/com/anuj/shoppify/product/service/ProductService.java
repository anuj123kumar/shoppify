package com.anuj.shoppify.product.service;


import com.anuj.shoppify.product.dto.ProductCategoryDTO;
import com.anuj.shoppify.product.dto.ProductDTO;
import com.anuj.shoppify.product.entity.Product;
import com.anuj.shoppify.product.entity.ProductCategory;
import com.anuj.shoppify.product.repository.ProductCategoryRepository;
import com.anuj.shoppify.product.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public void addProduct(List<ProductDTO> productDTOS) {
        List<Product> products = productDTOS.stream().map(dto -> {
            ProductCategory category = productCategoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + dto.getCategoryId()));

            return Product.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .price(dto.getPrice())
                    .category(category)
                    .build();
        }).toList();

        productRepository.saveAll(products);
        ResponseEntity.ok("Products Added");
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOPage = productPage.map(prod -> new ProductDTO(
                prod.getId(),
                prod.getName(),
                prod.getDescription(),
                prod.getPrice(),
                prod.getCategory().getId()
        ));
        return productDTOPage;
    }

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product Found with id " + id));
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        return productDTO;
    }

    public Page<ProductDTO> getProductByCategory(Long categoryId, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);
        return productPage.map(product -> new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory().getId()
        ));
    }

    public void removeProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product Found with id " + id));
        productRepository.delete(product);
    }

    public void updateProduct(long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not Found"));
        ProductCategory category = productCategoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        BeanUtils.copyProperties(productDTO,product);
        productRepository.save(product);
        ResponseEntity.ok("Product Updated");
    }

    //categories crud operation

    public void addCategory(ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = ProductCategory.builder()
                .id(productCategoryDTO.getId())
                .name(productCategoryDTO.getName())
                .build();
        productCategoryRepository.save(productCategory);
        ResponseEntity.ok("Product Category Added");
    }

    public Page<ProductCategoryDTO> getCategories(Pageable pageable) {
        Page<ProductCategory> productCategories = productCategoryRepository.findAll(pageable);
        Page<ProductCategoryDTO> productCategoryDTOPage = productCategories.map(cat -> new ProductCategoryDTO(
                cat.getId(),
                cat.getName()
        ));
        return productCategoryDTOPage;
    }

    public ProductCategoryDTO getCategoryById(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product Category Found with id " + id));
        return new ProductCategoryDTO(
                productCategory.getId(),
                productCategory.getName()
        );
    }

    public void updateCategory(Long id, ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product Category Found with id " + id));
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        productCategoryRepository.save(productCategory);
        ResponseEntity.ok("Product Updated");
    }

    public void removeCategory(Long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("No Product Category Found with id " + id));
        productCategoryRepository.delete(productCategory);
    }

}
