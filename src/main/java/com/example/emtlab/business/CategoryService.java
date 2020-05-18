package com.example.emtlab.business;

import com.example.emtlab.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    Category editById(Long id, Category category);

}
