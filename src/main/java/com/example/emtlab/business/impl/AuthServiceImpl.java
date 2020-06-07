package com.example.emtlab.business.impl;

import com.example.emtlab.business.AuthService;
import com.example.emtlab.business.UserService;
import com.example.emtlab.exceptions.PasswordDoesntMatchException;
import com.example.emtlab.exceptions.UserNotFoundException;
import com.example.emtlab.model.Role;
import com.example.emtlab.model.User;
import com.example.emtlab.repository.RoleRepository;
import com.example.emtlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        if (!this.userRepository.existsById("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(this.passwordEncoder.encode("admin"));
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }
    }

    @Override
    public User getCurrentUser() {
        //        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        return userRepository.findById(username).orElseThrow(()->new UserNotFoundException(username));

    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }

    @Override
    @Transactional
    public User signUpUser(String username,
                           String password,
                           String repeatedPassword) {
        if (!password.equals(repeatedPassword)) {
            throw new PasswordDoesntMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);
    }
}
