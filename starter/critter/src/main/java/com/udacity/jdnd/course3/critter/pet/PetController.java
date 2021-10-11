package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.model.Customer;
import com.udacity.jdnd.course3.critter.model.Employee;
import com.udacity.jdnd.course3.critter.model.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = new Pet();

        newPet.setName(petDTO.getName());
        newPet.setType(petDTO.getType());
        Optional<Customer> ownerOptional = this.customerService.get(petDTO.getOwnerId());

        if (ownerOptional.isPresent()) {
            Customer owner = ownerOptional.get();
            if (owner != null) {
                newPet.setOwner(owner);

                if (owner.getPets() == null) {
                    owner.setPets(new ArrayList<>());
                }
                owner.getPets().add(newPet);
            }
        }

        Pet savedPet = this.petService.add(newPet);
        //petDTO.setId(savedPet.getId());
        petDTO = this.toPetDTO(savedPet);

        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<Pet> petOptional = this.petService.get(petId);

        if (petOptional.isPresent()) {
            return this.toPetDTO(petOptional.get());
        }

        return null;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = this.petService.getAll();
        List<PetDTO> petDTOs = new ArrayList<>();

        for(Pet p: pets){
            petDTOs.add(this.toPetDTO(p));
        }

        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOs = new ArrayList<>();
        Optional<Customer> ownerOptional = this.customerService.get(ownerId);

        if (ownerOptional.isPresent()) {
            Customer owner = ownerOptional.get();
            if (owner != null) {
                for(Pet p: owner.getPets()){
                    petDTOs.add(this.toPetDTO(p));
                }

            }
        }

        return petDTOs;
    }

    public PetDTO toPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getOwner().getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());

        return petDTO;
    }
}
