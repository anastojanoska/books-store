package com.example.emtlab.business.impl;

import com.example.emtlab.business.BookService;
import com.example.emtlab.business.CartItemService;
import com.example.emtlab.business.UserService;
import com.example.emtlab.exceptions.ShoppingCartIsNotActiveException;
import com.example.emtlab.model.Book;
import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ShoppingCart;
import com.example.emtlab.model.User;
import com.example.emtlab.model.enumeration.CartStatus;
import com.example.emtlab.repository.CartItemRepository;
import com.example.emtlab.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem findById(Long id){
        return this.cartItemRepository.getOne(id);
    }



    @Override
    public CartItem createCartItem(Long bookId, Long shoppingCartId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(ShoppingCartIsNotActiveException::new);
        Book book = this.bookService.findById(bookId);
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);
        this.shoppingCartRepository.save(shoppingCart);

        return this.cartItemRepository.save(cartItem);
    }

    @Override
    public ShoppingCart addCartItemToShoppingCart(String userId, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
        Book book = this.bookService.findById(bookId);
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCart);

        this.cartItemRepository.save(cartItem);

        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Transactional
    @Override
    public ShoppingCart removeCartItemFromShoppingCart(String userId, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
        List<CartItem> cartItems = this.findAllByShoppingCartId(shoppingCart.getId());

        for(CartItem item : cartItems){
            if(item.getBook().getId().equals(bookId)){
                cartItemRepository.deleteById(item.getId());
                break;
            }
        }

        return this.shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getActiveShoppingCartOrCreateNew(String userId) {
        return this.shoppingCartRepository
                .findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userService.findById(userId);
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });

    }

    @Override
    @Transactional
    public List<CartItem> findAllByShoppingCartId(Long shoppingCartId) {
        List<CartItem> cartItems = cartItemRepository.findAll();

        return cartItems.stream()
                .filter(item->shoppingCartId.equals(item.getShoppingCart().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id){
        this.cartItemRepository.deleteById(id);
    }

    @Override
    public void deleteByBookId(Long bookId) {
        cartItemRepository.findAll()
                .stream()
                .forEach(item->{
                    if(item.getBook().getId().equals(bookId))
                        cartItemRepository.deleteById(item.getId());
                });
    }


}
