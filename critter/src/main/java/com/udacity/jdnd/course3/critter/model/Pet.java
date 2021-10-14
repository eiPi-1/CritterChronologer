package com.udacity.jdnd.course3.critter.model;

import javax.persistence.*;

import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Nationalized;
import java.time.LocalDate;

@Entity
@Table(name = "pet", catalog ="critter")
public class Pet {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    //@Column(name="pet_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="PET_TYPE", length = 128)
    private PetType type;

    @Nationalized
    private String name;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Customer owner;

    private LocalDate birthDate;

    @Nationalized
    private String notes;


    public Pet() {

    }

    public Pet(Long id, String name, Customer owner, PetType type) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
    }

    /* getters and setters*/

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

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
