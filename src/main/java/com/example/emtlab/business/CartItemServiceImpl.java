package com.example.emtlab.business;

import com.example.emtlab.model.Book;
import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ShoppingCart;
import com.example.emtlab.model.enumeration.CartStatus;
import com.example.emtlab.repository.CartItemRepository;
import com.example.emtlab.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService{

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
        ShoppingCart shoppingCart = this.shoppingCartRepository.getOne(shoppingCartId);
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

    @Override
    public ShoppingCart removeCartItemFromShoppingCart(String userId, Long bookId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
        List<CartItem> cartItems = this.cartItemRepository.findAll();

        for(CartItem item : cartItems){
            if(item.getBook().getId().equals(bookId))
                this.deleteById(item.getId());
        }

        return this.shoppingCartRepository.save(shoppingCart);
    }


    private ShoppingCart getActiveShoppingCartOrCreateNew(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if (shoppingCart == null){
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(userService.findById(userId));
            shoppingCart = this.shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;

    }

    @Override
    public List<CartItem> findAllByShoppingCartId(Long shoppingCartId) {
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItems.stream()
                .filter(item->item.getShoppingCart().getId().equals(shoppingCartId))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id){
        this.cartItemRepository.deleteById(id);
    }


}
