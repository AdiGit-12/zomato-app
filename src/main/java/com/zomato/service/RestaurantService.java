package com.zomato.service;

import com.zomato.entity.*;
import com.zomato.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final ReviewRepository reviewRepository;
    
    
    
    public RestaurantService(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository,
			MenuRepository menuRepository, MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.menuRepository = menuRepository;
		this.menuItemRepository = menuItemRepository;
		this.reviewRepository = reviewRepository;
	}

	public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
    
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
    
    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setName(restaurantDetails.getName());
        restaurant.setPhone(restaurantDetails.getPhone());
        restaurant.setEmail(restaurantDetails.getEmail());
        restaurant.setCity(restaurantDetails.getCity());
        restaurant.setArea(restaurantDetails.getArea());
        restaurant.setRating(restaurantDetails.getRating());
        return restaurantRepository.save(restaurant);
    }
    
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
    
    public List<Restaurant> getRestaurantsByCity(String city) {
        return restaurantRepository.findByCity(city);
    }
    
    public Restaurant addCategoryToRestaurant(Long restaurantId, Long categoryId) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        restaurant.getCategories().add(category);
        return restaurantRepository.save(restaurant);
    }
    
    public Menu addMenuToRestaurant(Long restaurantId, Menu menu) {
        Restaurant restaurant = getRestaurantById(restaurantId);
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }
    
    public MenuItem addMenuItemToMenu(Long menuId, MenuItem menuItem) {
        Menu menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("Menu not found"));
        menuItem.setMenu(menu);
        return menuItemRepository.save(menuItem);
    }
    
    public Double getRestaurantAverageRating(Long restaurantId) {
        Double avgRating = reviewRepository.getAverageRatingForRestaurant(restaurantId);
        return avgRating != null ? avgRating : 0.0;
    }
    
    public List<Review> getRestaurantReviews(Long restaurantId) {
        return reviewRepository.findByRestaurant_RestaurantId(restaurantId);
    }
}