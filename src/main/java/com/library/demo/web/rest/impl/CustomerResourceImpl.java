package com.library.demo.web.rest.impl;

import com.library.demo.persistence.mariadb.entity.CustomerEntity;
import com.library.demo.Repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;


@RestController
@Component
@RequestMapping("/api/customer")
public class CustomerResourceImpl {

    private final Logger log = LoggerFactory.getLogger(CustomerResourceImpl.class);
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerResourceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public Collection<CustomerEntity> customers() {
        return customerRepository.findAll();
    }
    
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        return customer.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/customer")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody CustomerEntity customerEntity) throws URISyntaxException {
        log.info("Request to create customer: {}", customerEntity);
        CustomerEntity result = customerRepository.save(customerEntity);
        return ResponseEntity.created(new URI("/api/group/" + result.getIdCustomer()))
                .body(result);
    }
    
    @PutMapping("/customer")
    public ResponseEntity<CustomerEntity> updateGroup(@RequestBody CustomerEntity customerEntity) {
        log.info("Request to update customer: {}", customerEntity);
        CustomerEntity result = customerRepository.save(customerEntity);
        return ResponseEntity.ok().body(result);
    }
    
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        log.info("Request to delete customer: {}", id);
        customerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
