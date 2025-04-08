package com.anuj.shoppify.product.controller;

import com.anuj.shoppify.product.dto.ProductCategoryDTO;
import com.anuj.shoppify.product.dto.ProductDTO;
import com.anuj.shoppify.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class ProductCategoryController {
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Void> addCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        productService.addCategory(productCategoryDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProductCategoryDTO>> getCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductCategoryDTO> response = productService.getCategories(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getCategoryById(@PathVariable Long id) {
        ProductCategoryDTO response = productService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO productCategoryDTO) {
        productService.updateCategory(id, productCategoryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> removeCategory(@PathVariable Long id) {
        productService.removeCategory(id);
        return ResponseEntity.ok().build();
    }
}
