package com.udacity.jdnd.course3.critter.model;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee", catalog ="critter")
public class Employee {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    @Column(name = "EMPLOYEE_ID", unique = true)
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "SKILLS", length = 250)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "DAYS_AVAILABLE", length = 250)
    private Set<DayOfWeek> days;

    public Employee() {

    }

    public Employee(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> availableDays) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.days = availableDays;
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

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return days;
    }

    public void setAvailableDates(Set<DayOfWeek> availableDays) {
        this.days = availableDays;
    }

}
