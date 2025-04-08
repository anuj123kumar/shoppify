package com.anuj.shoppify.cart.dto;

import com.anuj.shoppify.auth.dto.UserDTO;
import com.anuj.shoppify.auth.entity.User;
import com.anuj.shoppify.cart.OrderStatus;
import com.anuj.shoppify.cart.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;  // Ordering user
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private OrderStatus status; // NEW, PROCESSING, SHIPPED, DELIVERED, CANCELED
    private LocalDateTime orderDate;
}
