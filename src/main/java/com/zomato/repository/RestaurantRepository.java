package com.zomato.repository;

import com.zomato.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    // Derived queries
    List<Restaurant> findByCity(String city);
    List<Restaurant> findByCityAndArea(String city, String area);
    List<Restaurant> findByRatingGreaterThanEqual(Double rating);
    Optional<Restaurant> findByEmail(String email);
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    List<Restaurant> findByOrderByRatingDesc();
    
    // Custom JPQL query
    @Query("SELECT r FROM Restaurant r JOIN r.categories c WHERE c.categoryName = :categoryName")
    List<Restaurant> findRestaurantsByCategory(@Param("categoryName") String categoryName);
    
    // Native SQL query
    @Query(value = "SELECT * FROM restaurant WHERE rating > :minRating ORDER BY rating DESC LIMIT :limit", 
           nativeQuery = true)
    List<Restaurant> findTopRatedRestaurants(@Param("minRating") Double minRating, 
                                              @Param("limit") int limit);
    
    // Count queries
    long countByCity(String city);
    long countByRatingGreaterThanEqual(Double rating);
}