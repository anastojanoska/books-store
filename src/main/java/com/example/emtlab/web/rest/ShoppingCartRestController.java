package com.example.emtlab.web.rest;

import com.example.emtlab.business.AuthService;
import com.example.emtlab.business.ShoppingCartService;
import com.example.emtlab.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AuthService authService;

    @PostMapping
    public ShoppingCart createShoppingCart(){
        return this.shoppingCartService.create(this.authService.getCurrentUserId());
    }

    @DeleteMapping
    public ShoppingCart cancelActiveShoppingCart(){
        return this.shoppingCartService.cancelActiveShoppingCart(this.authService.getCurrentUserId());
    }

    @PostMapping("/checkout")
    public ShoppingCart checkoutActiveShoppingCart(){
//        return this.shoppingCartService.checkoutActiveShoppingCart(authService.getCurrentUserId());
    return null;
    }


}
