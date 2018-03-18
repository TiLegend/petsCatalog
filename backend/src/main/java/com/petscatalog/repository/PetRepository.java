package com.petscatalog.repository;

import com.petscatalog.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long>{

}
