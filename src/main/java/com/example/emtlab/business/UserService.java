package com.example.emtlab.business;

import com.example.emtlab.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(String username);
}
