package com.example.emtlab.business;


import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ShoppingCart;

import java.util.List;

public interface CartItemService {

    CartItem createCartItem(Long bookId, Long shoppingCartId);
    ShoppingCart addCartItemToShoppingCart(String userId, Long bookId);
    ShoppingCart removeCartItemFromShoppingCart(String userId, Long bookId);
    List<CartItem> findAll();
    CartItem findById(Long id);
    List<CartItem> findAllByShoppingCartId(Long shoppingCartId);
    void deleteById(Long id);
    void deleteByBookId(Long bookId);

}
