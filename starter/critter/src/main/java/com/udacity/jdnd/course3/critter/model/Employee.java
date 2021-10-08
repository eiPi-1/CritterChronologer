package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name="employee_id")
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    private List<EmployeeSkill> skills;

    @ElementCollection
    private List<LocalDate> availableDates;

    public Employee() {

    }

    public Employee(Long id, String name, List<EmployeeSkill> skills, List<LocalDate> availableDates) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.availableDates = availableDates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public List<LocalDate> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<LocalDate> availableDates) {
        this.availableDates = availableDates;
    }

}
