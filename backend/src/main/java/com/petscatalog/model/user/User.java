package com.petscatalog.model.user;

import com.petscatalog.model.pet.Pet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.peer.ListPeer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;

    @Column(name = "login_attempt_count")
    private Byte loginAttempt;

    @Column(name = "first_wrong_login_attempt_start")
    private LocalDateTime firstWrongLoginAttempStart;

    @OneToMany(
            mappedBy="owner",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Pet> pets = new ArrayList<>();

    User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
