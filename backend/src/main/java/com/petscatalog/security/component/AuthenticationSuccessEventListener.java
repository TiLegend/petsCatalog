package com.petscatalog.security.component;

import com.petscatalog.security.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

/**
 * Created by Tlegen on 05.01.2018.
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>
{
    @Autowired
    LoginAttemptService loginAttemptService;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event)
    {
        User userDetails = (User) event.getAuthentication().getPrincipal();
        loginAttemptService.loginSucceeded(userDetails.getUsername());

    }
}
