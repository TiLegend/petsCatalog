package com.petscatalog.security.service;

import com.petscatalog.model.user.User;
import com.petscatalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Created by Tlegen on 05.01.2018.
 */
@Service
public class LoginAttemptService {

    @Autowired
    UserRepository userRepository;

    @Value("${login.max.attempt}")
    private byte maxAttemp;

    public void loginSucceeded(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
        resetUserAttemp(user);
    }

    public void loginFailed(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()) {
            LocalDateTime failStartTime = Optional.ofNullable(userOptional.get().getFirstWrongLoginAttempStart()).orElse(LocalDateTime.now());
            if (failStartTime.until(LocalDateTime.now(), ChronoUnit.HOURS) > 1) failStartTime = LocalDateTime.now();
            userOptional.get().setFirstWrongLoginAttempStart(failStartTime);

            Byte userAttemptCount = Optional.ofNullable(userOptional.get().getLoginAttempt()).orElse(new Byte("0"));
            userAttemptCount++;
            userOptional.get().setLoginAttempt(userAttemptCount);

            userRepository.saveAndFlush(userOptional.get());
        }
    }

    public boolean isBlocked(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
        LocalDateTime failStartTime = user.getFirstWrongLoginAttempStart();
        Byte userAttemptCount = user.getLoginAttempt();

        if (failStartTime != null && failStartTime.until( LocalDateTime.now(), ChronoUnit.HOURS)>1) {
            resetUserAttemp(user);
            return false;
        }

        if(userAttemptCount !=null && failStartTime != null && userAttemptCount >= maxAttemp) {
            return true;
        }

        return false;

    }

    private void resetUserAttemp(User user){
        if(user.getLoginAttempt()!=null || user.getFirstWrongLoginAttempStart()!=null) {
            user.setLoginAttempt(null);
            user.setFirstWrongLoginAttempStart(null);
            userRepository.saveAndFlush(user);
        }
    }


}
