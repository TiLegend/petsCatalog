package com.petscatalog.service;

import com.petscatalog.exception.CustomException;
import com.petscatalog.model.user.User;
import com.petscatalog.model.user.UserDto;
import com.petscatalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.petscatalog.common.Constants.*;

/**
 * Created by Tlegen on 08.01.2018.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void addNewUser(UserDto userDto) throws CustomException {
        User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()));
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new CustomException("username is exist", USERNAME_IS_EXIST);
        }
        userRepository.saveAndFlush(user);
    }

    public User getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return userRepository.findByUsername(authentication.getName()).get();
    }


}
