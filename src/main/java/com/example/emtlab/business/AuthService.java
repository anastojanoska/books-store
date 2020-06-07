package com.example.emtlab.business;

import com.example.emtlab.model.User;

public interface AuthService {
    User getCurrentUser();
    String getCurrentUserId();
    User signUpUser(String username, String password, String repeatedPassword);

}
