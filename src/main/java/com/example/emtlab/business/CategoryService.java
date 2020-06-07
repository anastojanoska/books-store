package com.example.emtlab.business;

import com.example.emtlab.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    Category editById(Long id, Category category);

}
