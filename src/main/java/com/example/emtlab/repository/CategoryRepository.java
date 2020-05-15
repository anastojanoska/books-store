package com.example.emtlab.repository;

import com.example.emtlab.model.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    Category editById(Long id, Category category);
}
