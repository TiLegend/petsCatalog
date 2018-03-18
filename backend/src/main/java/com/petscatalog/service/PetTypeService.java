package com.petscatalog.service;

import com.petscatalog.model.pet.Pet;
import com.petscatalog.model.pet.PetType;
import com.petscatalog.model.pet.PetTypeDto;
import com.petscatalog.repository.PetTypeRepository;
import com.petscatalog.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PetTypeService {
    @Autowired
    PetTypeRepository petTypeRepository;
    private final DtoConvert dtoConvert = new DtoConvert();

    public Set<PetTypeDto> getPetTypes(){
        Set<PetTypeDto> petTypeDtos = new HashSet<>();
        for (PetType petType:petTypeRepository.findAll()) {
            petTypeDtos.add(dtoConvert.petTypeToDto(petType));
        }
        return petTypeDtos;
    }
}
