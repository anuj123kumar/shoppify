package com.anuj.shoppify.cart.service;

import com.anuj.shoppify.auth.entity.User;
import com.anuj.shoppify.auth.repository.UserRepository;
import com.anuj.shoppify.auth.service.AuthService;
import com.anuj.shoppify.cart.dto.CartDTO;
import com.anuj.shoppify.cart.dto.CartItemDTO;
import com.anuj.shoppify.cart.entity.Cart;
import com.anuj.shoppify.cart.entity.CartItem;
import com.anuj.shoppify.cart.repository.CartItemRepository;
import com.anuj.shoppify.cart.repository.CartRepository;
import com.anuj.shoppify.product.entity.Product;
import com.anuj.shoppify.product.repository.ProductRepository;
import com.anuj.shoppify.util.LoggedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private AuthService authService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    public CartDTO getCart() {
        List<CartItemDTO> cartItemDTOs = new ArrayList<>();
        Cart cart = getCartForUser(LoggedUserUtil.getCurrentUserId());
        for(CartItem item: cart.getItems()){
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setCartId(item.getId());
            cartItemDTO.setQuantity(item.getQuantity());
            cartItemDTO.setProductId(item.getProduct().getId());
            cartItemDTOs.add(cartItemDTO);
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartItemS(cartItemDTOs);
        cartDTO.setUser(cart.getUser().getEmail());
        return cartDTO;
    }

    public void addItemToCart(CartItemDTO cartItemDTO) {
        Cart cart = getCartForUser(LoggedUserUtil.getCurrentUserId());
        Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow(()->new RuntimeException("Product not Found"));
        CartItem item = cart.getItems().stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findFirst()
                .orElse(new CartItem(null, cart, product, 0));

        item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
        cart.getItems().add(cartItemRepository.save(item));
        cartRepository.save(cart);
    }

    public void removeItemFromCart(Long productId) {
        Cart cart = getCartForUser(LoggedUserUtil.getCurrentUserId());
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    public void clearCart() {
        Cart cart = getCartForUser(LoggedUserUtil.getCurrentUserId());
        cart.getItems().clear();
        cartRepository.delete(cart);
    }
}
