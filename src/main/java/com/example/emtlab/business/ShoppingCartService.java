package com.example.emtlab.business;

import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ShoppingCart;


public interface ShoppingCartService {

    ShoppingCart create(String userId);
//    ShoppingCart addCartItemToShoppingCart(String userId, Long cartItemId);
//    ShoppingCart removeCartItemFromShoppingCart(String userId, Long cartItemId);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart checkoutActiveShoppingCart(String userId);


}
