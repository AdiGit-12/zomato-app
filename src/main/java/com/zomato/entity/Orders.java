package com.zomato.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private DeliveryPerson deliveryPerson;
    
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    
    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Column(name = "order_status", nullable = false)
    private String orderStatus = "PLACED";
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    
    // Default constructor
    public Orders() {}
    
    // Constructor with fields
    public Orders(Long orderId, Customer customer, Restaurant restaurant, 
                  DeliveryPerson deliveryPerson, LocalDateTime orderDate, 
                  Double totalAmount, String orderStatus, 
                  List<OrderItem> orderItems, Payment payment) {
        this.orderId = orderId;
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryPerson = deliveryPerson;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
        this.payment = payment;
    }
    
    // Getters
    public Long getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Restaurant getRestaurant() { return restaurant; }
    public DeliveryPerson getDeliveryPerson() { return deliveryPerson; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Double getTotalAmount() { return totalAmount; }
    public String getOrderStatus() { return orderStatus; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public Payment getPayment() { return payment; }
    
    // Setters
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
    public void setDeliveryPerson(DeliveryPerson deliveryPerson) { this.deliveryPerson = deliveryPerson; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    public void setPayment(Payment payment) { this.payment = payment; }
    
    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "Orders [orderId=" + orderId + ", customer=" + customer + ", restaurant=" + restaurant
				+ ", deliveryPerson=" + deliveryPerson + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount
				+ ", orderStatus=" + orderStatus + ", orderItems=" + orderItems + ", payment=" + payment + "]";
	}
    
    
}