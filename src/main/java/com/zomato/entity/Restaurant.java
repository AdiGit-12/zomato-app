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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurant")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "phone", length = 15)
    private String phone;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "city", length = 50)
    private String city;
    
    @Column(name = "area", length = 50)
    private String area;
    
    @Column(name = "rating")
    private Double rating;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @ManyToMany
    @JoinTable(
        name = "restaurant_category",
        joinColumns = @JoinColumn(name = "restaurant_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();
    
    @OneToMany(mappedBy = "restaurant")
    private List<Orders> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviews = new ArrayList<>();
    
    // Default constructor
    public Restaurant() {}
    
    // Constructor with fields
    public Restaurant(Long restaurantId, String name, String phone, String email, 
                      String city, String area, Double rating, LocalDateTime createdDate,
                      List<Category> categories, List<Menu> menus, 
                      List<Orders> orders, List<Review> reviews) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.area = area;
        this.rating = rating;
        this.createdDate = createdDate;
        this.categories = categories;
        this.menus = menus;
        this.orders = orders;
        this.reviews = reviews;
    }
    
    // Getters
    public Long getRestaurantId() { return restaurantId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getCity() { return city; }
    public String getArea() { return area; }
    public Double getRating() { return rating; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public List<Category> getCategories() { return categories; }
    public List<Menu> getMenus() { return menus; }
    public List<Orders> getOrders() { return orders; }
    public List<Review> getReviews() { return reviews; }
    
    // Setters
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }
    public void setArea(String area) { this.area = area; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
    public void setMenus(List<Menu> menus) { this.menus = menus; }
    public void setOrders(List<Orders> orders) { this.orders = orders; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "Restaurant [restaurantId=" + restaurantId + ", name=" + name + ", phone=" + phone + ", email=" + email
				+ ", city=" + city + ", area=" + area + ", rating=" + rating + ", createdDate=" + createdDate
				+ ", categories=" + categories + ", menus=" + menus + ", orders=" + orders + ", reviews=" + reviews
				+ "]";
	}
}