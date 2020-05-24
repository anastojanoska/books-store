package com.example.emtlab.repository;

import com.example.emtlab.model.ShoppingCart;
import com.example.emtlab.model.enumeration.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    boolean existsByUserUsernameAndStatus(String userId, CartStatus created);

    ShoppingCart findByUserUsernameAndStatus(String userId, CartStatus created);
}
