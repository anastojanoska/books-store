package com.example.emtlab.web;

import com.example.emtlab.business.AuthorService;
import com.example.emtlab.business.BookService;
import com.example.emtlab.model.Author;
import com.example.emtlab.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    @GetMapping
    @Transactional
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @PostMapping
    public Author create(@Valid Author author){
        return authorService.create(author.getName());
    }

    @GetMapping("/{authorId}")
    @Transactional
    public Author findById(@PathVariable Long authorId){
        return authorService.findById(authorId);
    }

    @GetMapping("/{authorId}/books")
    @Transactional
    public List<Book> getAllBooksByAuthor(@PathVariable Long authorId){
        return authorService.getAllBooksByAuthor(authorId);
    }

    @GetMapping("/{bookId}/book")
    @Transactional
    public List<Author> getAllAuthorsByBook(@PathVariable Long bookId){
        return authorService.getAllAuthorsByBook(bookId);
    }

    @PostMapping("/{authorId}/{bookId}")
    @Transactional
    public List<Book> addBookToAuthor(@PathVariable Long bookId, @PathVariable Long authorId){
        Book book = bookService.findById(bookId);
        return authorService.addBookToAuthor(book, authorId);
    }

    @DeleteMapping("/{authorId}/{bookId}")
    @Transactional
    public void deleteBook(@PathVariable Long authorId, @PathVariable Long bookId){
        this.authorService.deleteBook(bookId, authorId);
    }



}
