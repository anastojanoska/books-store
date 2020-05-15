package com.example.emtlab.repository;

import com.example.emtlab.model.Book;
import com.example.emtlab.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BookRepositoryImpl implements BookRepository {

    private HashMap<Long,Book> books;
    private Long counter;

    @PostConstruct
    public void loadProperties(){
        this.books = new HashMap<>();
        this.counter = 1L;

        Long id = generateId();
        Book book1 = new Book(id, "Harry Potter", 5, new Category(1L, "Fantasy","description1"));
        books.put(id, book1);

        id = generateId();
        Book book2 = new Book(id, "The Great Gatsby", 3, new Category(1L, "Romance","description2"));
        books.put(id, book2);

        id = generateId();
        Book book3 = new Book(id, "Life of Pi", 4, new Category(1L, "Adventure","description3"));
        books.put(id, book3);

        id = generateId();
        Book book4 = new Book(id, "Narnia", 4, new Category(1L, "Fantasy","description4"));
        books.put(id, book4);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(this.books.values());
    }

    @Override
    public Book findById(Long id) {
        return this.books.get(id);
    }

    @Override
    public void save(Book book) {
        if(book.getId() == null) {
            book.setId(generateId());
        }
        books.put(book.getId(), book);
    }

    @Override
    public void deleteById(Long id) {
        this.books.remove(id);
    }

    private Long generateId(){
        return counter++;
    }
}
