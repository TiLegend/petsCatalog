package com.petscatalog.controller;

import com.petscatalog.common.enums.Sex;
import com.petscatalog.exception.CustomException;
import com.petscatalog.model.pet.Pet;
import com.petscatalog.model.pet.PetDto;
import com.petscatalog.repository.PetRepository;
import com.petscatalog.service.PetService;
import com.petscatalog.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Tlegen
 */
//@CrossOrigin()
@RestController
@RequestMapping(value="/api")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;

    private final DtoConvert dtoConvert = new DtoConvert();


    @GetMapping(value = "/pets")
    public List userPets() throws CustomException {
        return petService.getCurrentUserPets();
    }

    @GetMapping(value = "/ppp")
    public String index4(){
        return "Hello ppp";
    }

    @GetMapping(value = "/private")
    public String privateArea(){
        return "Private area";
    }

    @GetMapping(value="/genders")
    public ResponseEntity<List> petGenders(){
        List<Map<String,String>> genders = new ArrayList<>();
        for (Sex sex : Sex.values()) {
            Map map = new HashMap();
            map.put("value", sex.name());
            map.put("label", sex.name());
            genders.add(map);
        }
        return new ResponseEntity<>(genders, HttpStatus.OK);
    }

    @PostMapping(value = "/pet")
    public ResponseEntity<PetDto> savePet(@RequestBody PetDto petDto) throws CustomException {
        return new ResponseEntity<>(petService.saveOwnPet(petDto), HttpStatus.OK);
    }

    @DeleteMapping(value="/pet/delete/{id}")
    public ResponseEntity<Boolean> deletePet(@PathVariable Long id) throws CustomException {
        return  new ResponseEntity<>(petService.deleteOwnPet(id), HttpStatus.OK);
    }
    @GetMapping(value="/pet/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable Long id){
        Pet pet = petRepository.findOne(id);
        return new ResponseEntity<>(pet!=null ? dtoConvert.petToDto(pet):null, HttpStatus.OK);
    }

}
