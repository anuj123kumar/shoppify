package com.anuj.shoppify.cart.controller;

import com.anuj.shoppify.cart.dto.OrderDTO;
import com.anuj.shoppify.cart.dto.OrderItemDTO;
import com.anuj.shoppify.cart.dto.OrderSummaryDTO;
import com.anuj.shoppify.cart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder() {
        return ResponseEntity.ok(orderService.placeOrder());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/user")
    public ResponseEntity<Page<OrderDTO>> getUserOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrdersByUser(pageable));
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long orderId, @RequestBody List<OrderItemDTO> updatedItems) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, updatedItems));
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orderSummary")
    public ResponseEntity<Page<OrderSummaryDTO>> getOrderSummariesForUser(Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrderSummariesForUser(pageable));
    }
}
