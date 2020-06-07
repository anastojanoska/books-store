package com.example.emtlab.business;

import com.example.emtlab.model.ChargeRequest;
import com.example.emtlab.model.ShoppingCart;


public interface ShoppingCartService {
    ShoppingCart findActiveShoppingCartByUsername(String userId);
    ShoppingCart create(String userId);
    ShoppingCart cancelActiveShoppingCart(String userId);
    ShoppingCart checkoutActiveShoppingCart(String userId, ChargeRequest chargeRequest);


}
