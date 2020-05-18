package com.example.emtlab.business;

import com.example.emtlab.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();
    Optional<Book> findById(Long id);
    void save(Book book, MultipartFile multipartFile) throws IOException;
    void deleteById(Long id);

}
