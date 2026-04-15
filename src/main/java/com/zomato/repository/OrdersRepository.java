package com.zomato.repository;

import com.zomato.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomer_CustomerId(Long customerId);
    List<Orders> findByRestaurant_RestaurantId(Long restaurantId);
    List<Orders> findByOrderStatus(String status);
}