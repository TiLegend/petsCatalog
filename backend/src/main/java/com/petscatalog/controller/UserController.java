package com.petscatalog.controller;

import com.petscatalog.model.user.UserDto;
import com.petscatalog.repository.UserRepository;
import com.petscatalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Tlegen
 */
@CrossOrigin()
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserService userService;

    @Autowired
    TokenEndpoint tokenEndpoint;

    @GetMapping(value = "/registration_check/{username}")
    public ResponseEntity<Map> checkUserExist(@PathVariable String username){
        Map<String, Object> checkResponse =new HashMap<>();
        checkResponse.put("username", username);
        checkResponse.put("isExist", userRepository.findByUsername(username).isPresent());
        return new ResponseEntity<>(checkResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<OAuth2AccessToken> registrationUser(@RequestBody UserDto userDto) throws Exception {
        Map<String,String> parameters = new HashMap();
        userService.addNewUser(userDto);
        parameters.put("client_secret", "secret");
        parameters.put("client_id", "web");
        parameters.put("grant_type", "password");
        parameters.put("username", userDto.getUsername());
        parameters.put("password", userDto.getPassword());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User principal = new User("web", "secret", new ArrayList<>());
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, "secret",
                new HashSet<>());
        result.setDetails(authentication.getDetails());

        return tokenEndpoint.postAccessToken(result, parameters);
    }

}
