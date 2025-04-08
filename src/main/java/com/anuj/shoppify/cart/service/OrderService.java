package com.anuj.shoppify.cart.service;

import com.anuj.shoppify.auth.entity.User;
import com.anuj.shoppify.auth.repository.UserRepository;
import com.anuj.shoppify.cart.OrderStatus;
import com.anuj.shoppify.cart.dto.OrderDTO;
import com.anuj.shoppify.cart.dto.OrderItemDTO;
import com.anuj.shoppify.cart.dto.OrderSummaryDTO;
import com.anuj.shoppify.cart.entity.Cart;
import com.anuj.shoppify.cart.entity.Orders;
import com.anuj.shoppify.cart.entity.OrderItem;
import com.anuj.shoppify.cart.repository.CartRepository;
import com.anuj.shoppify.cart.repository.OrderItemRepository;
import com.anuj.shoppify.cart.repository.OrderRepository;
import com.anuj.shoppify.product.entity.Product;
import com.anuj.shoppify.product.repository.ProductRepository;
import com.anuj.shoppify.util.LoggedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;


    public OrderDTO placeOrder() {
        Long userId = LoggedUserUtil.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findById(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot place order.");
        }

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setStatus(OrderStatus.NEW);
        orders.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(null, orders, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getProduct().getPrice()))
                .collect(Collectors.toList());

        orders.setOrderItems(orderItems);
        orders.setTotalAmount(orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        orderRepository.save(orders);
        cartService.clearCart();
        return mapToDTO(orders);
    }

    public OrderDTO getOrderById(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("No order Found"));
        return mapToDTO(orders);
    }

    private OrderDTO mapToDTO(Orders orders) {
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem item: orders.getOrderItems()){
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setId(item.getId());
            orderItemDTO.setProductId(item.getProduct().getId());
            orderItemDTO.setProductName(item.getProduct().getName());
            orderItemDTO.setPrice(item.getPrice());
            orderItemDTO.setQuantity(item.getQuantity());
            orderItemDTOS.add(orderItemDTO);
        }

        return new OrderDTO(orders.getId(), LoggedUserUtil.getCurrentUserId(),orderItemDTOS, orders.getStatus(), orders.getOrderDate());
    }

    public Page<OrderDTO> getOrdersByUser(Pageable pageable) {
        User user = userRepository.findById(LoggedUserUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Page<Orders> orders = orderRepository.findByUserId(LoggedUserUtil.getCurrentUserId(),pageable);
        return orders.map(this::mapToDTO);
    }


    public OrderDTO updateOrder(Long orderId, List<OrderItemDTO> updatedItems) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> newItems = new ArrayList<>();
        for (OrderItemDTO dto : updatedItems) {
            Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItem item = new OrderItem();
            item.setOrders(orders);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            newItems.add(item);
        }

        orderItemRepository.deleteAll(orders.getOrderItems());
        orders.setOrderItems(newItems);
        orderRepository.save(orders);
        return mapToDTO(orders);
    }

    public void cancelOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("No order Found"));
        orders.setStatus(OrderStatus.CANCELED);
        orderRepository.save(orders);
        ResponseEntity.ok("Order has been Cancelled");
    }

    public Page<OrderSummaryDTO> getOrderSummariesForUser(Pageable pageable) {
        Page<Orders> orders = orderRepository.findByUserId(LoggedUserUtil.getCurrentUserId(),pageable);
        return orders.map(order -> {
            OrderSummaryDTO dto = new OrderSummaryDTO();
            dto.setOrderId(order.getId());
            dto.setStatus(order.getStatus());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderDate(order.getOrderDate());
            return dto;
        });
    }

}
