package com.example.emtlab.web.rest;

import com.example.emtlab.business.BookService;
import com.example.emtlab.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    public List<Book> findAll() {
        return this.bookService.findAll();
    }

}
