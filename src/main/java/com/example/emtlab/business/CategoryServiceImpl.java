package com.example.emtlab.business;

import com.example.emtlab.exceptions.CategoryNotFoundException;
import com.example.emtlab.model.Book;
import com.example.emtlab.model.Category;
import com.example.emtlab.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    private BookService bookService;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(()->new CategoryNotFoundException(id));
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        for(Book book:bookService.findAll()){
            if(book.getCategory().getId().equals(id)) {
                book.setCategory(null);
            }
        }
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Category editById(Long id, Category category) {
        //TODO: da se proveri uste ednas

         Category cat = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFoundException(id));
         cat = category;
         categoryRepository.save(cat);
         return cat;
    }
}
