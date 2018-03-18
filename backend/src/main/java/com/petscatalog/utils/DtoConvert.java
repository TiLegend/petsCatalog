package com.petscatalog.utils;

import com.petscatalog.model.pet.Pet;
import com.petscatalog.model.pet.PetDto;
import com.petscatalog.model.pet.PetType;
import com.petscatalog.model.pet.PetTypeDto;
import com.petscatalog.model.user.User;
import com.petscatalog.model.user.UserDto;

/**
 * Created by Tlegen on 09.01.2018.
 */
public class DtoConvert {
    public PetDto petToDto(Pet pet) {
        return new PetDto(pet.getId(), pet.getNickname(), pet.getBirthDay(), pet.getSex(), new PetTypeDto(pet.getPetType().getId(), pet.getPetType().getPetTypeName()));
    }

    public PetTypeDto petTypeToDto(PetType petType) {
        return new PetTypeDto(petType.getId(),petType.getPetTypeName());
    }

}
