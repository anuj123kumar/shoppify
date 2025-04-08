package com.anuj.shoppify.cart.dto;

import com.anuj.shoppify.cart.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private OrderStatus status;
}
