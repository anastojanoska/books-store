package com.example.emtlab.business.impl;

import com.example.emtlab.business.*;
import com.example.emtlab.exceptions.BookOutOfStockException;
import com.example.emtlab.exceptions.ShoppingCartAlreadyExists;
import com.example.emtlab.exceptions.ShoppingCartIsNotActiveException;
import com.example.emtlab.exceptions.TransactionFailedException;
import com.example.emtlab.model.*;
import com.example.emtlab.model.enumeration.CartStatus;
import com.example.emtlab.repository.ShoppingCartRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private PaymentService paymentService;

    @Override
    @Transactional
    public ShoppingCart findActiveShoppingCartByUsername(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(()->new ShoppingCartIsNotActiveException(userId));
        return shoppingCart;
    }

    @Override
    public ShoppingCart create(String userId) {
        User user = userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED))
            throw new ShoppingCartAlreadyExists();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));

        shoppingCart.setStatus(CartStatus.CANCELED);
        shoppingCart.setTimeCanceledOrFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutActiveShoppingCart(String userId, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActiveException(userId));

        List<CartItem> cartItems = cartItemService.findAllByShoppingCartId(shoppingCart.getId());
        float price = 0;
        for(CartItem item : cartItems) {
            if (item.getBook().getSample() <= 0)
                throw new BookOutOfStockException(item.getBook().getId());
            price += item.getBook().getPrice();
            item.getBook().setSample(item.getBook().getSample() - 1);
        }
            try {
                Charge charge = this.paymentService.pay(chargeRequest);
            }catch (StripeException ex){
                throw new TransactionFailedException(userId, ex.getMessage());
            }

        shoppingCart.setStatus(CartStatus.FINISHED);
        shoppingCart.setTimeCanceledOrFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
