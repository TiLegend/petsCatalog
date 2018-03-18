package com.petscatalog.service;

import com.petscatalog.exception.CustomException;
import com.petscatalog.model.pet.Pet;
import com.petscatalog.model.pet.PetDto;
import com.petscatalog.model.pet.PetType;
import com.petscatalog.model.user.User;
import com.petscatalog.repository.PetRepository;
import com.petscatalog.repository.PetTypeRepository;
import com.petscatalog.repository.UserRepository;
import com.petscatalog.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.petscatalog.common.Constants.*;

/**
 * Created by Tlegen on 12.01.2018.
 */
@Service
public class PetService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    UserService userService;

    @Autowired
    PetTypeRepository petTypeRepository;

    private final DtoConvert dtoConvert = new DtoConvert();

    @Transactional
    public List<PetDto> getCurrentUserPets() throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> userOp = userRepository.findByUsername(currentPrincipalName);
        if(!userOp.isPresent()){
            throw new CustomException("username is not exist", USERNAME_IS_NOT_EXIST);
        }

        List<PetDto> petDtos = new ArrayList<>();
        for (Pet pet:userOp.get().getPets()) {
            petDtos.add(dtoConvert.petToDto(pet));
        }

        return petDtos;

    }

    public PetDto saveOwnPet(PetDto petDto) throws CustomException {
        User authUser = userService.getAuthUser();
        Pet pet = new Pet();
        if(petDto.getId()!=null){
            pet = petRepository.findOne(petDto.getId());

            if (pet == null)  throw new CustomException("The selected pet ID does not exist", PET_IS_NOT_EXIST);

            if(!authUser.getUsername().equalsIgnoreCase(pet.getOwner().getUsername())) throw new CustomException("You do not own a pet", YOU_NOT_OWN_EDIT_PET);
        }

        pet.setNickname(petDto.getNickname());
        pet.setBirthDay(petDto.getBirthDay());
        pet.setOwner(authUser);
        pet.setSex(petDto.getSex());
            PetType petType = petTypeRepository.findOne(petDto.getPetType().getId());
            if( petType == null ) throw new CustomException("The selected pet type ID does not exist", SELECTED_PET_TYPE_NOT_EXIST);
            pet.setPetType(petType);

        petRepository.saveAndFlush(pet);
        return dtoConvert.petToDto(pet);
    }



    public Boolean deleteOwnPet(Long id) throws CustomException {
        User authUser = userService.getAuthUser();
        Pet pet = petRepository.findOne(id);
        if(pet == null) throw new CustomException("The selected pet type ID does not exist", SELECTED_PET_TYPE_NOT_EXIST);
        if(!authUser.getUsername().equalsIgnoreCase(pet.getOwner().getUsername())) throw new CustomException("You do not own a pet", YOU_NOT_OWN_EDIT_PET);
        petRepository.delete(id);
        return !petRepository.exists(id);
    }
}
