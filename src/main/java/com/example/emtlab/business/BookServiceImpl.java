package com.example.emtlab.business;

import com.example.emtlab.model.Book;
import com.example.emtlab.model.Category;
import com.example.emtlab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    @Autowired
    private CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {
         this.bookRepository.save(book);
    }

//    @Override
//    public Book saveBook(Book book, MultipartFile image) throws IOException {
//        Category category = this.categoryService.findById(book.getCategory().getId());
//        book.setCategory(category);
//        if (image != null && !image.getName().isEmpty()) {
//            byte[] bytes = image.getBytes();
//            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
//            book.setImage(image);
//            book.setImageBase64(base64Image);
//        }
//        this.bookRepository.save(book);
//        return book;
//    }


    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }
}
