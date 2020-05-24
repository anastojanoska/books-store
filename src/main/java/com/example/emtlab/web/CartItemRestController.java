package com.example.emtlab.web;

import com.example.emtlab.business.AuthService;
import com.example.emtlab.business.CartItemService;
import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemRestController {

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private AuthService authService;

    @GetMapping
    public List<CartItem> getAllCartItems(){
        return cartItemService.findAll();
    }

    @PostMapping("/{bookId}/book/{shoppingCartId}")
    public CartItem createCartItem(@PathVariable Long bookId, @PathVariable Long shoppingCartId){
        return cartItemService.createCartItem(bookId, shoppingCartId);
    }

    @PatchMapping("/{bookId}")
    public ShoppingCart addCartItemToShoppingCart(@PathVariable Long bookId){
        return this.cartItemService.addCartItemToShoppingCart(this.authService.getCurrentUserId(),bookId);
    }

    @DeleteMapping("/{bookId}")
    public ShoppingCart removeCartItemFromShoppingCart(@PathVariable Long bookId){
        return this.cartItemService.removeCartItemFromShoppingCart(
                this.authService.getCurrentUserId(), bookId);
    }

    @GetMapping("/{shoppingCartId}")
    public List<CartItem> findAllByShoppingCartId(@PathVariable Long shoppingCartId){
        return this.cartItemService.findAllByShoppingCartId(shoppingCartId);
    }
}
