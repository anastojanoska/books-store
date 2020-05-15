package com.example.emtlab.repository;

import com.example.emtlab.model.Category;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryRepositoryImpl implements CategoryRepository {

    private HashMap<Long, Category> categories;
    private Long counter;

    @PostConstruct
    public void loadProperties(){
        categories = new HashMap<>();
        this.counter = 0L;

        Long id = generateId();
        Category category1 = new Category(id, "Adventure", "description1");
        categories.put(id, category1);

        id = generateId();
        Category category2 = new Category(id, "Fantasy", "description2");
        categories.put(id, category2);


        id = generateId();
        Category category3 = new Category(id, "Mystery", "description3");
        categories.put(id, category3);

    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Category findById(Long id) {
        return categories.get(id);
    }

    @Override
    public Category save(Category category) {
        if(category.getId() == null) {
            category.setId(generateId());
        }
        categories.put(category.getId(), category);

        return category;
    }

    @Override
    public void deleteById(Long id) {
        categories.remove(id);
    }

    @Override
    public Category editById(Long id, Category category) {
        this.categories.put(id, category);
        return category;
    }

    private Long generateId(){
        return counter++;
    }

}
