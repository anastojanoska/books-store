package com.example.emtlab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BookIsAlreadyInShoppingCart extends RuntimeException {
    public BookIsAlreadyInShoppingCart(){
        super("Book is already in shopping cart");
    }
}
