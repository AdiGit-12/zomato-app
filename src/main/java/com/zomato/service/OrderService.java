package com.zomato.service;

import com.zomato.entity.*;
import com.zomato.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final PaymentRepository paymentRepository;
    
    
    public OrderService(OrdersRepository ordersRepository, CustomerRepository customerRepository,
			RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository,
			PaymentRepository paymentRepository) {
		super();
		this.ordersRepository = ordersRepository;
		this.customerRepository = customerRepository;
		this.restaurantRepository = restaurantRepository;
		this.menuItemRepository = menuItemRepository;
		this.paymentRepository = paymentRepository;
	}

	public Orders createOrder(Long customerId, Long restaurantId, List<OrderItemRequest> items) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        
        Orders order = new Orders();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setOrderStatus("PLACED");
        
        double totalAmount = 0.0;
        for (OrderItemRequest itemReq : items) {
            MenuItem menuItem = menuItemRepository.findById(itemReq.getItemId())
                .orElseThrow(() -> new RuntimeException("Menu item not found"));
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(menuItem.getPrice());
            order.getOrderItems().add(orderItem);
            
            totalAmount += menuItem.getPrice() * itemReq.getQuantity();
        }
        
        order.setTotalAmount(totalAmount);
        return ordersRepository.save(order);
    }
    
    public Payment processPayment(Long orderId, String paymentMode) {
        Orders order = ordersRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMode(paymentMode);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentStatus("SUCCESS");
        
        order.setOrderStatus("CONFIRMED");
        ordersRepository.save(order);
        
        return paymentRepository.save(payment);
    }
    
    public Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    public List<Orders> getOrdersByCustomer(Long customerId) {
        return ordersRepository.findByCustomer_CustomerId(customerId);
    }
    
    public Orders updateOrderStatus(Long orderId, String status) {
        Orders order = getOrderById(orderId);
        order.setOrderStatus(status);
        return ordersRepository.save(order);
    }
    
    public static class OrderItemRequest {
        private Long itemId;
        private Integer quantity;
        
        public Long getItemId() { return itemId; }
        public void setItemId(Long itemId) { this.itemId = itemId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}