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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    
    @Column(name = "menu_name", nullable = false, length = 100)
    private String menuName;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems = new ArrayList<>();
    
    // Default constructor
    public Menu() {}
    
    // Constructor with fields
    public Menu(Long menuId, Restaurant restaurant, String menuName, 
                LocalDateTime createdDate, List<MenuItem> menuItems) {
        this.menuId = menuId;
        this.restaurant = restaurant;
        this.menuName = menuName;
        this.createdDate = createdDate;
        this.menuItems = menuItems;
    }
    
    // Getters
    public Long getMenuId() { return menuId; }
    public Restaurant getRestaurant() { return restaurant; }
    public String getMenuName() { return menuName; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public List<MenuItem> getMenuItems() { return menuItems; }
    
    // Setters
    public void setMenuId(Long menuId) { this.menuId = menuId; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
    public void setMenuName(String menuName) { this.menuName = menuName; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public void setMenuItems(List<MenuItem> menuItems) { this.menuItems = menuItems; }
    
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", restaurant=" + restaurant + ", menuName=" + menuName + ", createdDate="
				+ createdDate + ", menuItems=" + menuItems + "]";
	}
    
    
}