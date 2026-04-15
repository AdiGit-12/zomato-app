package com.zomato.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zomato.entity.Menu;
import com.zomato.entity.MenuItem;
import com.zomato.entity.Restaurant;
import com.zomato.entity.Review;
import com.zomato.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantController {
	
    private final RestaurantService restaurantService;
    
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, 
                                                        @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCity(@PathVariable String city) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByCity(city));
    }
    
    @PostMapping("/{restaurantId}/categories/{categoryId}")
    public ResponseEntity<Restaurant> addCategoryToRestaurant(@PathVariable Long restaurantId, 
                                                               @PathVariable Long categoryId) {
        return ResponseEntity.ok(restaurantService.addCategoryToRestaurant(restaurantId, categoryId));
    }
    
    @PostMapping("/{restaurantId}/menus")
    public ResponseEntity<Menu> addMenuToRestaurant(@PathVariable Long restaurantId, 
                                                     @RequestBody Menu menu) {
        return ResponseEntity.ok(restaurantService.addMenuToRestaurant(restaurantId, menu));
    }
    
    @PostMapping("/menus/{menuId}/items")
    public ResponseEntity<MenuItem> addMenuItemToMenu(@PathVariable Long menuId, 
                                                       @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(restaurantService.addMenuItemToMenu(menuId, menuItem));
    }
    
    @GetMapping("/{restaurantId}/reviews")
    public ResponseEntity<List<Review>> getRestaurantReviews(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantReviews(restaurantId));
    }
    
    @GetMapping("/{restaurantId}/rating")
    public ResponseEntity<Double> getRestaurantRating(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantAverageRating(restaurantId));
    }
}