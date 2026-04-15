package com.zomato.controller;

import com.zomato.entity.*;
import com.zomato.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;
    
    
    
    public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
    public ResponseEntity<Orders> createOrder(@RequestParam Long customerId,
                                              @RequestParam Long restaurantId,
                                              @RequestBody List<Map<String, Object>> items) {
        List<OrderService.OrderItemRequest> orderItems = new ArrayList<>();
        for (Map<String, Object> item : items) {
            OrderService.OrderItemRequest req = new OrderService.OrderItemRequest();
            req.setItemId(Long.valueOf(item.get("itemId").toString()));
            req.setQuantity(Integer.valueOf(item.get("quantity").toString()));
            orderItems.add(req);
        }
        Orders order = orderService.createOrder(customerId, restaurantId, orderItems);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Payment> processPayment(@PathVariable Long orderId,
                                                  @RequestParam String paymentMode) {
        return ResponseEntity.ok(orderService.processPayment(orderId, paymentMode));
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }
    
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable Long orderId,
                                                    @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
}