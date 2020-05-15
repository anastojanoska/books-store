package com.example.emtlab.business;

import com.example.emtlab.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    List<Book> findAll();
    Book findById(Long id);
    void save(Book book);
    void deleteById(Long id);
//    Book saveBook(Book book, MultipartFile image) throws IOException;

}