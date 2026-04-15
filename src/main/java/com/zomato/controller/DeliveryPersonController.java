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

import com.zomato.entity.DeliveryPerson;
import com.zomato.repository.DeliveryPersonRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/delivery-persons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeliveryPersonController {
	
    private final DeliveryPersonRepository deliveryPersonRepository;
    
    
    public DeliveryPersonController(DeliveryPersonRepository deliveryPersonRepository) {
		super();
		this.deliveryPersonRepository = deliveryPersonRepository;
	}

	@PostMapping
    public ResponseEntity<DeliveryPerson> createDeliveryPerson(@RequestBody DeliveryPerson deliveryPerson) {
        return new ResponseEntity<>(deliveryPersonRepository.save(deliveryPerson), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<DeliveryPerson>> getAllDeliveryPersons() {
        return ResponseEntity.ok(deliveryPersonRepository.findAll());
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<DeliveryPerson>> getAvailableDeliveryPersons() {
        return ResponseEntity.ok(deliveryPersonRepository.findByStatus("AVAILABLE"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryPerson> getDeliveryPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryPersonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery person not found")));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryPerson> updateDeliveryPerson(@PathVariable Long id,
                                                               @RequestBody DeliveryPerson deliveryPerson) {
        DeliveryPerson existing = deliveryPersonRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery person not found"));
        existing.setName(deliveryPerson.getName());
        existing.setPhone(deliveryPerson.getPhone());
        existing.setVehicleNo(deliveryPerson.getVehicleNo());
        existing.setStatus(deliveryPerson.getStatus());
        return ResponseEntity.ok(deliveryPersonRepository.save(existing));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliveryPerson(@PathVariable Long id) {
        deliveryPersonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}