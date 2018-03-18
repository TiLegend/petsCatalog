package com.petscatalog.model.pet;

import com.petscatalog.common.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PetDto {
    private Long id;
    private String nickname;
    private LocalDate birthDay;
    private Sex sex;
    private PetTypeDto petType;
}
