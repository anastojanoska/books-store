package com.example.emtlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShoppingCartIsNotActiveException extends  RuntimeException{
    public ShoppingCartIsNotActiveException(String userId){
        super("There isn't active shopping cart, please add item to activate it");
    }

    public ShoppingCartIsNotActiveException(){
        super("Shopping cart doesn't exist");
    }
}
