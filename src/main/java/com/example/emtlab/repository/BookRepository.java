package com.example.emtlab.repository;

import com.example.emtlab.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByCategoryId(Long id);
    List<Book> findAllByCategoryId(Long categoryId);
}
