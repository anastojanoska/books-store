package com.example.emtlab.business;

import com.example.emtlab.model.User;

public interface UserService {
    User findById(String username);
}
