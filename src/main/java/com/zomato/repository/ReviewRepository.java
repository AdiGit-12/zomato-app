package com.zomato.repository;

import com.zomato.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurant_RestaurantId(Long restaurantId);
    List<Review> findByCustomer_CustomerId(Long customerId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.restaurant.restaurantId = :restaurantId")
    Double getAverageRatingForRestaurant(Long restaurantId);
}