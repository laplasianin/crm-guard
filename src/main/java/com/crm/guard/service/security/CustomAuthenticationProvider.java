package com.crm.guard.service.security;

import com.crm.guard.entity.User;
import com.crm.guard.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();
        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();

        User user = userService.findById(userName);
        if (user != null) {

            if (!shaPasswordEncoder.isPasswordValid(user.getPassword(), rawPassword, user.getName())) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(user, rawPassword, userService.findAuthorities(user));
        }

        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}