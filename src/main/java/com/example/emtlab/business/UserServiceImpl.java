package com.example.emtlab.business;

import com.example.emtlab.exceptions.UserAlreadyExistsException;
import com.example.emtlab.exceptions.UserNotFoundException;
import com.example.emtlab.model.User;
import com.example.emtlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(String username) {
        return userRepository.findById(username).orElseThrow(()->new UserNotFoundException(username));
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (this.userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findById(s)
                .orElseThrow(()->new UsernameNotFoundException(s));
    }
}
