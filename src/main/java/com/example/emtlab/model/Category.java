package com.example.emtlab.model;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class Category {

    private Long id;
    @NotNull
    private String name;
    private String description;

    public Category(){}

    public Category(@NotNull String name){
        this.name = name;
    }

    public Category(Long id, @NotNull String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
