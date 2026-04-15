package com.zomato.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "delivery_person")
public class DeliveryPerson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "phone", unique = true, length = 15)
    private String phone;
    
    @Column(name = "vehicle_no", length = 20)
    private String vehicleNo;
    
    @Column(name = "status", nullable = false)
    private String status = "AVAILABLE";
    
    @OneToMany(mappedBy = "deliveryPerson")
    private List<Orders> orders = new ArrayList<>();
    
    // Default constructor
    public DeliveryPerson() {}
    
    // Constructor with fields
    public DeliveryPerson(Long deliveryId, String name, String phone, 
                          String vehicleNo, String status, List<Orders> orders) {
        this.deliveryId = deliveryId;
        this.name = name;
        this.phone = phone;
        this.vehicleNo = vehicleNo;
        this.status = status;
        this.orders = orders;
    }
    
    // Getters
    public Long getDeliveryId() { return deliveryId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getVehicleNo() { return vehicleNo; }
    public String getStatus() { return status; }
    public List<Orders> getOrders() { return orders; }
    
    // Setters
    public void setDeliveryId(Long deliveryId) { this.deliveryId = deliveryId; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }
    public void setStatus(String status) { this.status = status; }
    public void setOrders(List<Orders> orders) { this.orders = orders; }
    
 // ========== HELPER METHOD ==========
    public void updateFrom(DeliveryPerson source) {
        if (source.getName() != null) {
            this.name = source.getName();
        }
        if (source.getPhone() != null) {
            this.phone = source.getPhone();
        }
        if (source.getVehicleNo() != null) {
            this.vehicleNo = source.getVehicleNo();
        }
        if (source.getStatus() != null) {
            this.status = source.getStatus();
        }
    }
    
    @Override
    public String toString() {
        return "DeliveryPerson{" +
                "deliveryId=" + deliveryId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}