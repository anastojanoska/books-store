package com.example.emtlab.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, message = "Name must be > 3")
    private String name;
    @NotNull
    @Min(value = 1, message = "Sample must be > 0")
    private Integer sample;
    private Integer price;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Category category;
    @Lob
    private String base64image;

}
