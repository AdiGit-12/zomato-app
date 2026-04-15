package com.zomato.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column(name = "house_no", length = 20)
    private String houseNo;
    
    @Column(name = "street", length = 100)
    private String street;
    
    @Column(name = "city", length = 50)
    private String city;
    
    @Column(name = "pincode", length = 10)
    private String pincode;
    
    @Column(name = "address_type", length = 10)
    private String addressType;
    
    // Default constructor
    public Address() {}
    
    // Constructor with fields
    public Address(Long addressId, Customer customer, String houseNo, String street,
                   String city, String pincode, String addressType) {
        this.addressId = addressId;
        this.customer = customer;
        this.houseNo = houseNo;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
        this.addressType = addressType;
    }
    
    // Getters
    public Long getAddressId() { return addressId; }
    public Customer getCustomer() { return customer; }
    public String getHouseNo() { return houseNo; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPincode() { return pincode; }
    public String getAddressType() { return addressType; }
    
    // Setters
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }
    public void setStreet(String street) { this.street = street; }
    public void setCity(String city) { this.city = city; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public void setAddressType(String addressType) { this.addressType = addressType; }

	@Override
	public String toString() {
		return "Address [addressId=" + addressId + ", customer=" + customer + ", houseNo=" + houseNo + ", street="
				+ street + ", city=" + city + ", pincode=" + pincode + ", addressType=" + addressType + "]";
	}
    
    
}