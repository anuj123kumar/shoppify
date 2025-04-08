package com.anuj.shoppify.cart.controller;

import com.anuj.shoppify.auth.service.CustomUserDetailsService;
import com.anuj.shoppify.cart.dto.CartDTO;
import com.anuj.shoppify.cart.dto.CartItemDTO;
import com.anuj.shoppify.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        CartDTO response = cartService.getCart();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody CartItemDTO cartItemDTO) {
        cartService.addItemToCart(cartItemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long productId) {
        cartService.removeItemFromCart(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
