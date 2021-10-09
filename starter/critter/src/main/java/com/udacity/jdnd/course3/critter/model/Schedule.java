package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name="schedule_id")
    private Long id;

    private LocalDate date;

    @ElementCollection
    private List<EmployeeSkill> activities;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @ElementCollection
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "pet_id") )
    @ElementCollection
    private List<Pet> pets;

    public Schedule() {
    }

    public Schedule(Long id, LocalDate date, List<EmployeeSkill> activities, List<Employee> employees, List<Pet> pets) {
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

    public List<EmployeeSkill> getActivities() {
        return activities;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setActivities(List<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
