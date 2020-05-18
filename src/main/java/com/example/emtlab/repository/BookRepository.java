package com.example.emtlab.repository;

import com.example.emtlab.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//    List<Book> findAll();
//    Book findById(Long id);
//    void save(Book book);
//    void deleteById(Long id);
}
