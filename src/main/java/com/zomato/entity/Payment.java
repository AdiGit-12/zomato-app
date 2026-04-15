package com.zomato.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;
    
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Orders order;
    
    @Column(name = "payment_mode", nullable = false, length = 10)
    private String paymentMode;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @Column(name = "payment_status", nullable = false, length = 10)
    private String paymentStatus = "PENDING";
    
    // Default constructor
    public Payment() {}
    
    // Constructor with fields
    public Payment(Long paymentId, Orders order, String paymentMode, 
                   LocalDateTime paymentDate, Double amount, String paymentStatus) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentMode = paymentMode;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
    
    // Getters
    public Long getPaymentId() { return paymentId; }
    public Orders getOrder() { return order; }
    public String getPaymentMode() { return paymentMode; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public Double getAmount() { return amount; }
    public String getPaymentStatus() { return paymentStatus; }
    
    // Setters
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public void setOrder(Orders order) { this.order = order; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "Payment [paymentId=" + paymentId + ", order=" + order + ", paymentMode=" + paymentMode
				+ ", paymentDate=" + paymentDate + ", amount=" + amount + ", paymentStatus=" + paymentStatus + "]";
	}
    
    
}