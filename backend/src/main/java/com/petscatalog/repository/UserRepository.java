package com.petscatalog.repository;

import com.petscatalog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Tlegen
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);


}
