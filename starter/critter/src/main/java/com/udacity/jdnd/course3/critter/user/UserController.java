package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    EmployeeService employeeService;
    CustomerService customerService;
    PetService petService;

    @Autowired
    public UserController(EmployeeService employeeService, CustomerService customerService, PetService petService) {
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer newCustomer = new Customer();

        newCustomer.setName(customerDTO.getName());
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
        newCustomer.setNotes(customerDTO.getNotes());

        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();

        if (petIds != null)
        {
            for(Long petId: customerDTO.getPetIds()){
                Optional<Pet> petOptional = this.petService.get(petId);

                if (petOptional.isPresent()) {
                    pets.add(petOptional.get());
                }
            }
        }

        newCustomer.setPets(pets);
        Customer savedCustomer = this.customerService.add(newCustomer);
        customerDTO.setId(savedCustomer.getId());

        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = this.customerService.getAll();
        List<CustomerDTO> customerDTOs = customers.stream().map(this::toCustomerDTO).collect(Collectors.toList());

        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Optional<Pet> petOptional = this.petService.get(petId);

        if (petOptional.isPresent()) {
            return this.toCustomerDTO(petOptional.get().getOwner());
        }

        return null;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee newEmployee = new Employee();

        newEmployee.setName(employeeDTO.getName());
        newEmployee.setSkills(employeeDTO.getSkills());
        newEmployee.setAvailableDates(employeeDTO.getDaysAvailable());

        Employee savedEmployee = this.employeeService.add(newEmployee);

        employeeDTO.setId(savedEmployee.getId());

        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<Employee> employeeOptional = this.employeeService.get(employeeId);

        if (employeeOptional.isPresent()) {
            return this.toEmployeeDTO(employeeOptional.get());
        }

        return null;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Optional<Employee> employeeOptional = this.employeeService.get(employeeId);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setAvailableDates(daysAvailable);
            this.employeeService.add(employee); // updating (not adding) in this case
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> matchingEmployees = employeeService.getMatching(employeeDTO.getSkills(),
                employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> matchingEmployeeDTOs = matchingEmployees.stream().map(
                this::toEmployeeDTO).collect(Collectors.toList());
        return matchingEmployeeDTOs;
    }

    public EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(new HashSet<>(employee.getSkills()));
        employeeDTO.setDaysAvailable(new HashSet<>(employee.getAvailableDays()));

        return employeeDTO;
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Long> petIds = new ArrayList<>();
        petIds.addAll(customer.getPetIds());
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

}
