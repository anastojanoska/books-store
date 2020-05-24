package com.example.emtlab.business;

import com.example.emtlab.model.User;
import com.example.emtlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        return userRepository.findById("emt-user")
                .orElseGet(()->{
                    User user = new User();
                    user.setUsername("emt-user");
                    return this.userRepository.save(user);
                });
    }

    @Override
    public String getCurrentUserId() {
        return this.getCurrentUser().getUsername();
    }
}
