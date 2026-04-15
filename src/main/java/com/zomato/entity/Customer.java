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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    
    @Column(name = "phone", unique = true, length = 15)
    private String phone;
    
    @Column(name = "email", unique = true, length = 100)
    private String email;
    
    @Column(name = "gender", length = 1)
    private String gender;
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer")
    private List<Orders> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "customer")
    private List<Review> reviews = new ArrayList<>();
    
    // Default constructor
    public Customer() {}
    
    // Constructor with fields
    public Customer(Long customerId, String fullName, String phone, String email, 
                    String gender, LocalDateTime registrationDate,
                    List<Address> addresses, List<Orders> orders, List<Review> reviews) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.registrationDate = registrationDate;
        this.addresses = addresses;
        this.orders = orders;
        this.reviews = reviews;
    }
    
    // Getters
    public Long getCustomerId() { return customerId; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public List<Address> getAddresses() { return addresses; }
    public List<Orders> getOrders() { return orders; }
    public List<Review> getReviews() { return reviews; }
    
    // Setters
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setGender(String gender) { this.gender = gender; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
    public void setOrders(List<Orders> orders) { this.orders = orders; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    
    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", fullName=" + fullName + ", phone=" + phone + ", email=" + email
				+ ", gender=" + gender + ", registrationDate=" + registrationDate + ", addresses=" + addresses
				+ ", orders=" + orders + ", reviews=" + reviews + "]";
	}
    
    
}