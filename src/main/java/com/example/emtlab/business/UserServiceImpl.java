package com.example.emtlab.business;

import com.example.emtlab.exceptions.UserNotFoundException;
import com.example.emtlab.model.User;
import com.example.emtlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(String username) {
        return userRepository.findById(username).orElseThrow(()->new UserNotFoundException(username));
    }
}
