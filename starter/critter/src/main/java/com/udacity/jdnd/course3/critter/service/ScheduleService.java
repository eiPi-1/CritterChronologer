package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           CustomerRepository customerRepository,
                           EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Schedule add(Schedule schedule){
        return this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getPetSchedules(Long petId){
        return this.scheduleRepository.getAllByPetsId(petId);
    }

    public List<Schedule> getEmployeeSchedules(Long employeeId){
        return this.scheduleRepository.getAllByEmployeesId(employeeId);
    }

    public List<Schedule> getCustomerSchedules(Long customerId) throws ObjectNotFoundException {
        List<Schedule> schedules = new ArrayList<>();
        String errMsg = "Customer with id=" + customerId.toString() + " not found!";

        Optional<Customer> optCustomer = this.customerRepository.findById(customerId);
        Customer customer = (Customer) optCustomer.orElseThrow(() -> new ObjectNotFoundException(errMsg));

        List<Long> petIds = customer.getPetIds();

        for(Long petId: petIds){
            schedules.addAll(this.scheduleRepository.getAllByPetsId(petId));
        }

        return schedules;
    }
}
