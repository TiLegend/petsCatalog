package com.petscatalog.security.service;

import com.petscatalog.repository.UserRepository;
import com.petscatalog.security.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Tlegen
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    LoginAttemptService loginAttemptService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked(username)){
            throw new RuntimeException("user blocked");
        }
        return userRepository
                .findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(user.getUsername()
                        , user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER")))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }
}
