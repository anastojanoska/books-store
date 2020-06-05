package com.example.emtlab.business;

import com.example.emtlab.exceptions.BookNotFoundException;
import com.example.emtlab.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> findAllByCategoryId(Long categoryId);
    List<Book> findAll();
    Book findById(Long id) throws BookNotFoundException;
    void save(Book book, MultipartFile multipartFile) throws IOException;
//    Book update(Long id, Book book, MultipartFile image) throws IOException;
    void deleteById(Long id);
    boolean existsByCategoryId(Long categoryId);

}
