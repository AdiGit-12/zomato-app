package com.zomato.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    
    @Column(name = "category_name", nullable = false, unique = true, length = 50)
    private String categoryName;
    
    @Column(name = "description", length = 200)
    private String description;
    
    @ManyToMany(mappedBy = "categories")
    private List<Restaurant> restaurants = new ArrayList<>();
    
    // Default constructor
    public Category() {}
    
    // Constructor with fields
    public Category(Long categoryId, String categoryName, String description, List<Restaurant> restaurants) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.restaurants = restaurants;
    }
    
    // Getters
    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public String getDescription() { return description; }
    public List<Restaurant> getRestaurants() { return restaurants; }
    
    // Setters
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public void setDescription(String description) { this.description = description; }
    public void setRestaurants(List<Restaurant> restaurants) { this.restaurants = restaurants; }
    
    public void updateFrom(Category other) {
        if (other.getCategoryName() != null) {
            this.categoryName = other.getCategoryName();
        }
        if (other.getDescription() != null) {
            this.description = other.getDescription();
        }
    }

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", categoryName=" + categoryName + ", description=" + description
				+ ", restaurants=" + restaurants + "]";
	}
    
    
}