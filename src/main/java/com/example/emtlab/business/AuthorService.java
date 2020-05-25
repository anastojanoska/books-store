package com.example.emtlab.business;

import com.example.emtlab.model.Author;
import com.example.emtlab.model.Book;

import java.util.List;

public interface AuthorService {
    Author create(String name);
    Author save(Author author);
    List<Author> getAllAuthors();
    List<Author> getAllAuthorsByBook(Long bookId);
    Author findById(Long id);
    List<Book> getAllBooksByAuthor(Long authorId);
    List<Book> addBookToAuthor(Book book, Long authorId);
    void deleteBook(Long bookId, Long authorId);
}
