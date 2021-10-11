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
    @GeneratedValue
    //@Column(name="schedule_id")
    private Long id;

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @ElementCollection
    private Set<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "pet_id") )
    @ElementCollection
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
