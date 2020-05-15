package com.example.emtlab.repository;

import com.example.emtlab.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();
    Book findById(Long id);
    void save(Book book);
    void deleteById(Long id);
}
