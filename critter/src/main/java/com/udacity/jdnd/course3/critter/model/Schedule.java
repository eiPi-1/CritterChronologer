package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    @Column(name = "SCHEDULE_ID", unique = true)
    private Long id;

    @Column(name="SCHEDULE_DATE")
    private LocalDate date;

    @ElementCollection
    @Column(name = "SCHEDULE_ACTIVITIES")
    private Set<EmployeeSkill> activities;

    @ManyToMany
    @Column(name = "SCHEDULE_EMPLOYEEIDS")
    private Set<Employee> employees;

    @ManyToMany
    @Column(name = "SCHEDULE_PETIDS")
    private Set<Pet> pets;

    public Schedule() {
    }

    public Schedule(Long id, LocalDate date, Set<EmployeeSkill> activities, Set<Employee> employees, Set<Pet> pets) {
        this.id = id;
        this.date = date;
        this.activities = activities;
        this.employees = employees;
        this.pets = pets;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
