package com.example.emtlab.web;

import com.example.emtlab.business.CategoryService;
import com.example.emtlab.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Category saveCategory(@Valid @RequestBody Category category){
        this.categoryService.save(category);
        return category;
    }

    @PostMapping("/{id}/delete")
    public void deleteById(@PathVariable Long id){
        this.categoryService.deleteById(id);
    }

//    @PostMapping("/{id}/edit")
//    public Category editById(@PathVariable Long id, @RequestBody Category category){
//        return this.categoryService.editById(id, category);
//    }
}
