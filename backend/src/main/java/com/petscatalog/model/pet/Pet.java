package com.petscatalog.model.pet;

import com.petscatalog.common.enums.Sex;
import com.petscatalog.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String nickname;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;

    @ManyToOne
    @JoinColumn(name = "pet_type_id", referencedColumnName="id")
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName="id")
    private User owner;

}
