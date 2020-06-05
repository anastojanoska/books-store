package com.example.emtlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShoppingCartIsNotActiveException extends  RuntimeException{
    public ShoppingCartIsNotActiveException(String userId){
        super(String.format("Shopping cart for user with id: %s is not active", userId));
    }

    public ShoppingCartIsNotActiveException(){
        super("Shopping cart doesn't exist");
    }
}
