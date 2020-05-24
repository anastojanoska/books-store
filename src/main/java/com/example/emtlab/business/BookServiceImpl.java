package com.example.emtlab.business;

import com.example.emtlab.exceptions.BookNotFoundException;
import com.example.emtlab.model.Book;
import com.example.emtlab.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(Long id)  {
        return this.bookRepository.findById(id)
                .orElseThrow(()-> new BookNotFoundException(id));
    }

    @Override
    public void save(Book book, MultipartFile image) throws IOException {

        if(image != null) {
            byte [] imageBytes = image.getBytes();
            String base64image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(imageBytes));
            book.setBase64image(base64image);
        }

        this.bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
