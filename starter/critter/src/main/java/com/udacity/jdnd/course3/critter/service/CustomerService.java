package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer add(Customer customer){
        return this.customerRepository.save(customer);
    }

    public Optional<Customer> get(Long id){
        return this.customerRepository.findById(id);
    }

    public List<Customer> getAll(){
        return this.customerRepository.findAll();
    }
}
