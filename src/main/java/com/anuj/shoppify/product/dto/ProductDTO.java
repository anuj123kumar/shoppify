package com.anuj.shoppify.product.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private long id;
    private String name;
    private String description;
    private double price;
    private Long categoryId;

}
