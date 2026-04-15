package com.zomato.controller;

import com.zomato.entity.Restaurant;
import com.zomato.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SearchController {
    private final RestaurantRepository restaurantRepository;
    
    public SearchController(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	@GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String keyword) {
        return ResponseEntity.ok(restaurantRepository.findByNameContainingIgnoreCase(keyword));
    }
}