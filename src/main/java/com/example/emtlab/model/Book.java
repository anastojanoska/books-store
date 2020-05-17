package com.example.emtlab.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Book {

    private Long id;
    @NotNull
    @Size(min = 3, message = "Name must be > 3")
    private String name;
    @NotNull
    @Min(value = 1, message = "Sample must be > 0")
    private Integer sample;
    private Category category;
    private String base64image;


    public Book(){}

    public Book(Long id) {
        this.id = id;
    }



    public Book(Long id, String name, @NotNull @Min(value = 1, message = "must be >0") Integer sample, Category category) {
        this.id = id;
        this.name = name;
        this.sample = sample;
        this.category = category;
    }

}
