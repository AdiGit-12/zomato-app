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
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final OrdersRepository ordersRepository;
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    
    
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository,
			OrdersRepository ordersRepository, ReviewRepository reviewRepository,
			RestaurantRepository restaurantRepository) {
		super();
		this.customerRepository = customerRepository;
		this.addressRepository = addressRepository;
		this.ordersRepository = ordersRepository;
		this.reviewRepository = reviewRepository;
		this.restaurantRepository = restaurantRepository;
	}

	public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setFullName(customerDetails.getFullName());
        customer.setPhone(customerDetails.getPhone());
        customer.setEmail(customerDetails.getEmail());
        customer.setGender(customerDetails.getGender());
        return customerRepository.save(customer);
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
    
    public Address addAddressToCustomer(Long customerId, Address address) {
        Customer customer = getCustomerById(customerId);
        address.setCustomer(customer);
        return addressRepository.save(address);
    }
    
    public List<Address> getCustomerAddresses(Long customerId) {
        return addressRepository.findByCustomer_CustomerId(customerId);
    }
    
    public List<Orders> getCustomerOrders(Long customerId) {
        return ordersRepository.findByCustomer_CustomerId(customerId);
    }
    
    public Review addReview(Long customerId, Long restaurantId, Review review) {
        Customer customer = getCustomerById(customerId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        return reviewRepository.save(review);
    }
    
    public List<Review> getCustomerReviews(Long customerId) {
        return reviewRepository.findByCustomer_CustomerId(customerId);
    }
}