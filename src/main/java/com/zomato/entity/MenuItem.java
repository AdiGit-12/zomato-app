package com.zomato.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu_item")
public class MenuItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;
    
    @Column(name = "item_name", nullable = false, length = 100)
    private String itemName;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "available_status", nullable = false)
    private String availableStatus = "YES";
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @OneToMany(mappedBy = "menuItem")
    private List<OrderItem> orderItems = new ArrayList<>();
    
    // Default constructor
    public MenuItem() {}
    
    // Constructor with fields
    public MenuItem(Long itemId, Menu menu, String itemName, Double price, 
                    String availableStatus, LocalDateTime createdDate, List<OrderItem> orderItems) {
        this.itemId = itemId;
        this.menu = menu;
        this.itemName = itemName;
        this.price = price;
        this.availableStatus = availableStatus;
        this.createdDate = createdDate;
        this.orderItems = orderItems;
    }
    
    // Getters
    public Long getItemId() { return itemId; }
    public Menu getMenu() { return menu; }
    public String getItemName() { return itemName; }
    public Double getPrice() { return price; }
    public String getAvailableStatus() { return availableStatus; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    
    // Setters
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public void setMenu(Menu menu) { this.menu = menu; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setPrice(Double price) { this.price = price; }
    public void setAvailableStatus(String availableStatus) { this.availableStatus = availableStatus; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "MenuItem [itemId=" + itemId + ", menu=" + menu + ", itemName=" + itemName + ", price=" + price
				+ ", availableStatus=" + availableStatus + ", createdDate=" + createdDate + ", orderItems=" + orderItems
				+ "]";
	}
    
    
}