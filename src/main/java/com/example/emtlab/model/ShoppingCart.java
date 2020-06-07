package com.example.emtlab.model;

import com.example.emtlab.model.enumeration.CartStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CartStatus status = CartStatus.CREATED;
    private LocalDateTime timeCreated = LocalDateTime.now();
    private LocalDateTime timeCanceledOrFinished;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @OneToMany(mappedBy = "shoppingCart")
    private List<CartItem> cartItems;

}
