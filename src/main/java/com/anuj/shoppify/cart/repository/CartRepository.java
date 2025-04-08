package com.anuj.shoppify.cart.repository;

import com.anuj.shoppify.auth.entity.User;
import com.anuj.shoppify.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
