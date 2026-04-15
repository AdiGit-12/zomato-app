package com.zomato.controller;

import com.zomato.entity.*;
import com.zomato.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.registerCustomer(customer), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
                                                    @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<Address> addAddress(@PathVariable Long customerId, 
                                               @RequestBody Address address) {
        return new ResponseEntity<>(customerService.addAddressToCustomer(customerId, address), 
                                   HttpStatus.CREATED);
    }
    
    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<List<Address>> getCustomerAddresses(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerAddresses(customerId));
    }
    
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<Orders>> getCustomerOrders(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerOrders(customerId));
    }
    
    @PostMapping("/{customerId}/reviews")
    public ResponseEntity<Review> addReview(@PathVariable Long customerId,
                                            @RequestParam Long restaurantId,
                                            @RequestBody Review review) {
        return new ResponseEntity<>(customerService.addReview(customerId, restaurantId, review), 
                                   HttpStatus.CREATED);
    }
    
    @GetMapping("/{customerId}/reviews")
    public ResponseEntity<List<Review>> getCustomerReviews(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerReviews(customerId));
    }
}