package com.example.emtlab.web;

import com.example.emtlab.business.AuthService;
import com.example.emtlab.business.CartItemService;
import com.example.emtlab.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart-items")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private AuthService authService;

    @PostMapping("/{bookId}/add-item")
    public String addItemToShoppingCart(@PathVariable Long bookId){
        try{
            ShoppingCart shoppingCart = this.cartItemService.addCartItemToShoppingCart(authService.getCurrentUserId(), bookId);
        }catch (RuntimeException ex){
            return "redirect:/books?error=" + ex.getLocalizedMessage();
        }
        return "redirect:/books?message=BookAdded";
    }

    @PostMapping("/{bookId}/remove-item")
    public String removeItemFromShoppingCart(@PathVariable Long bookId){
        ShoppingCart shoppingCart = this.cartItemService.removeCartItemFromShoppingCart(authService.getCurrentUserId(), bookId);
        return "redirect:/books?message=BookRemoved";
    }



}
