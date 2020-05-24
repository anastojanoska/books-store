package com.example.emtlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ShoppingCartAlreadyExists extends RuntimeException {
    public ShoppingCartAlreadyExists(){
        super("Shopping Cart Already Exists");
    }
}
