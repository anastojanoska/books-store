package com.example.emtlab.web;

import com.example.emtlab.business.CategoryService;
import com.example.emtlab.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(){
        return this.categoryService.findAll();
    }

    @PostMapping
    public Category saveCategory(@Valid Category category, BindingResult bindingResult) {
        return this.categoryService.save(category);
    }

    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id){
        this.categoryService.deleteById(id);
    }

    @PostMapping("/{id}/edit")
    public Category editById(@PathVariable Long id, @Valid Category category, BindingResult bindingResult){
        return this.categoryService.editById(id, category);
    }

}
