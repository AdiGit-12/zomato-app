package com.zomato.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    
    @Column(name = "rating", nullable = false)
    private Integer rating;
    
    @Column(name = "comments", length = 500)
    private String comments;
    
    @Column(name = "review_date")
    private LocalDateTime reviewDate;
    
    // Default constructor
    public Review() {}
    
    // Constructor with fields
    public Review(Long reviewId, Customer customer, Restaurant restaurant, 
                  Integer rating, String comments, LocalDateTime reviewDate) {
        this.reviewId = reviewId;
        this.customer = customer;
        this.restaurant = restaurant;
        this.rating = rating;
        this.comments = comments;
        this.reviewDate = reviewDate;
    }
    
    // Getters
    public Long getReviewId() { return reviewId; }
    public Customer getCustomer() { return customer; }
    public Restaurant getRestaurant() { return restaurant; }
    public Integer getRating() { return rating; }
    public String getComments() { return comments; }
    public LocalDateTime getReviewDate() { return reviewDate; }
    
    // Setters
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
    public void setRating(Integer rating) { this.rating = rating; }
    public void setComments(String comments) { this.comments = comments; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }
    
    @PrePersist
    protected void onCreate() {
        reviewDate = LocalDateTime.now();
    }

	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", customer=" + customer + ", restaurant=" + restaurant + ", rating="
				+ rating + ", comments=" + comments + ", reviewDate=" + reviewDate + "]";
	}
    
    
}