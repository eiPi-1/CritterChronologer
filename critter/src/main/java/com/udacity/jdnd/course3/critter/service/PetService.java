package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;
    CustomerRepository customerRepository;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet add(Pet pet){
        return this.petRepository.save(pet);
    }

    public Optional<Pet> get(Long id){
        return this.petRepository.findById(id);
    }

    public List<Pet> getAll(){
        return this.petRepository.findAll();
    }

    public List<Pet> getAllById(List<Long> ids){
        return this.petRepository.findAllById(ids);
    }


    public List<Pet> getAllByOwner(Long customerId){
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        Optional<Customer> customer = this.customerRepository.findById(customerId);

        if(customer.isPresent()){
            Customer cust = customer.get();
            petIds = cust.getPetIds();
            pets.addAll(this.getAllById(petIds));
        }

        return pets;
    }
}
