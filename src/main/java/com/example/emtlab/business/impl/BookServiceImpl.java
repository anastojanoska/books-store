package com.example.emtlab.business.impl;

import com.example.emtlab.business.AuthService;
import com.example.emtlab.business.BookService;
import com.example.emtlab.business.ShoppingCartService;
import com.example.emtlab.exceptions.BookIsAlreadyInShoppingCart;
import com.example.emtlab.exceptions.BookNotFoundException;
import com.example.emtlab.model.Book;
import com.example.emtlab.model.ShoppingCart;
import com.example.emtlab.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AuthService authService;

    @Override
    public List<Book> findAllByCategoryId(Long categoryId) {
        return this.bookRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) throws BookNotFoundException {
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
        Book book = bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        ShoppingCart shoppingCart = shoppingCartService.findActiveShoppingCartByUsername(authService.getCurrentUserId());
        shoppingCart.getCartItems().stream().forEach(item -> {
            if(item.getBook().getId().equals(book.getId()))
                throw new BookIsAlreadyInShoppingCart();
        });
        this.bookRepository.deleteById(id);
    }

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return this.bookRepository.existsByCategoryId(categoryId);
    }

}
