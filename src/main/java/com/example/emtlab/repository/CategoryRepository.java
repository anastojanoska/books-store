package com.example.emtlab.repository;

import com.example.emtlab.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Category editById(Long id, Category category);

//    List<Category> findAll();
//    Category findById(Long id);
//    Category save(Category category);
//    void deleteById(Long id);
//    Category editById(Long id, Category category);
}
