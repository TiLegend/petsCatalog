package com.petscatalog.model.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PetTypeDto {
    private Integer id;
    private String petTypeName;
}
