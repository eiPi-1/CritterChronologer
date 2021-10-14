package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.model.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    ScheduleService scheduleService;
    EmployeeService employeeService;
    CustomerService customerService;
    PetService petService;

    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService, CustomerService customerService, PetService petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = new Schedule();

        newSchedule.setDate(scheduleDTO.getDate());
        newSchedule.setActivities(scheduleDTO.getActivities());
        //newSchedule.setEmployees(scheduleDTO.getEmployeeIds());


        Set<Employee> employees = new HashSet<Employee>();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        if (employeeIds != null)
        {
            for(Long employeeId: employeeIds){
                Optional<Employee> employeeOptional = this.employeeService.get(employeeId);

                if (employeeOptional.isPresent()) {
                    employees.add(employeeOptional.get());
                }
            }
        }

        newSchedule.setEmployees(employees);

        Set<Pet> pets = new HashSet<Pet>();
        List<Long> petIds = scheduleDTO.getPetIds();

        if (petIds != null)
        {
            for(Long petId: petIds){
                Optional<Pet> petOptional = this.petService.get(petId);

                if (petOptional.isPresent()) {
                    pets.add(petOptional.get());
                }
            }
        }

        newSchedule.setPets(pets);

        Schedule savedSchedule = this.scheduleService.add(newSchedule);
        ScheduleDTO savedScheduleDTO = this.toScheduleDTO(savedSchedule);

        return savedScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = this.scheduleService.getAll();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        for (Schedule schedule: schedules){
            scheduleDTOs.add(this.toScheduleDTO(schedule));
        }

        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = this.scheduleService.getPetSchedules(petId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        for (Schedule schedule: schedules){
            scheduleDTOs.add(this.toScheduleDTO(schedule));
        }

        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = this.scheduleService.getEmployeeSchedules(employeeId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        for (Schedule schedule: schedules){
            scheduleDTOs.add(this.toScheduleDTO(schedule));
        }

        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = this.scheduleService.getCustomerSchedules(customerId);
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

        for (Schedule schedule: schedules){
            scheduleDTOs.add(this.toScheduleDTO(schedule));
        }

        return scheduleDTOs;
    }

    public ScheduleDTO toScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setActivities(schedule.getActivities());
        List<Long> employeeIds = new ArrayList<>();
        for(Employee employee: schedule.getEmployees()){
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Long> petIds = new ArrayList<>();
        for(Pet pet: schedule.getPets()){
            petIds.add(pet.getId());
        }

        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setDate(schedule.getDate());

        return scheduleDTO;
    }
}
