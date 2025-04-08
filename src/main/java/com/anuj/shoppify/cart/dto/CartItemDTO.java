package com.anuj.shoppify.cart.dto;

import com.anuj.shoppify.product.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartId;
    private Long productId;
    private int quantity;

}
