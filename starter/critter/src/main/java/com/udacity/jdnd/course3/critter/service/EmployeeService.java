package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee add(Employee employee){
        return this.employeeRepository.save(employee);
    }

    public Optional<Employee> get(Long id){
        return this.employeeRepository.findById(id);
    }

    public List<Employee> getAll(){
        return this.employeeRepository.findAll();
    }

    public List<Employee> getMatching(List<EmployeeSkill> requiredSkills, LocalDate date){
        List<Employee> availableEmployees = this.employeeRepository.getAvailableByDate(date);
        List<Employee> matchingEmployees = new ArrayList<>();

        for(Employee employee: availableEmployees){
            if(employee.getSkills().containsAll(requiredSkills)){
                matchingEmployees.add(employee);
            }
        }
        return matchingEmployees;
    }
}
