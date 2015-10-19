package com.crm.guard.service.api;

import com.crm.guard.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

public interface UserService {

    public void saveUser(User user);

    User findById(String username);

    User system();

    Set<GrantedAuthority> findAuthorities(User user);

    List<User> findAll();

    void createWorkUsers();
}