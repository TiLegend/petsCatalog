package com.petscatalog.controller;

import com.petscatalog.model.pet.PetTypeDto;
import com.petscatalog.service.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by Tlegen on 09.03.2018.
 */
@RestController
@RequestMapping(value="/api")
public class PetTypeController {

    @Autowired
    PetTypeService petTypeService;

    @GetMapping(value = "/pettypes")
    public ResponseEntity<Set<PetTypeDto>> getPetTypes(){
        return new ResponseEntity<Set<PetTypeDto>>(petTypeService.getPetTypes(), HttpStatus.OK);
    }
}
