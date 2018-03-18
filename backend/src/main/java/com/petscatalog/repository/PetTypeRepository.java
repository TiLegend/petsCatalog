package com.petscatalog.repository;

import com.petscatalog.model.pet.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Tlegen on 12.01.2018.
 */
public interface PetTypeRepository extends JpaRepository<PetType, Integer>{
}
