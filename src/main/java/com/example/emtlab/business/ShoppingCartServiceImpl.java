package com.example.emtlab.business;

import com.example.emtlab.exceptions.BookOutOfStockException;
import com.example.emtlab.exceptions.ShoppingCartAlreadyExists;
import com.example.emtlab.exceptions.ShoppingCartIsNotActiveException;
import com.example.emtlab.model.Book;
import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ShoppingCart;
import com.example.emtlab.model.User;
import com.example.emtlab.model.enumeration.CartStatus;
import com.example.emtlab.repository.ShoppingCartRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{


    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CartItemService cartItemService;

    @Override
    public ShoppingCart create(String userId) {
        User user = userService.findById(userId);
        if(this.shoppingCartRepository.existsByUserUsernameAndStatus(userId, CartStatus.CREATED))
            throw new ShoppingCartAlreadyExists();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);

        return this.shoppingCartRepository.save(shoppingCart);
    }

//    @Override
//    public ShoppingCart addCartItemToShoppingCart(String userId, Long bookId) {
//        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
//        Book book = this.bookService.findById(bookId);
//        CartItem cartItem = new CartItem();
//        cartItem.setBook(book);
//        cartItem.setShoppingCart(shoppingCart);
//
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
//
//    @Override
//    public ShoppingCart removeCartItemFromShoppingCart(String userId, Long cartItemId) {
//        ShoppingCart shoppingCart = this.getActiveShoppingCartOrCreateNew(userId);
//        shoppingCart.setBooks(
//                shoppingCart.getBooks()
//                        .stream()
//                        .filter(item -> !item.getId().equals(bookId))
//                        .collect(Collectors.toList())
//        );
//
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
//
//    private ShoppingCart getActiveShoppingCartOrCreateNew(String userId) {
//        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
//        if (shoppingCart == null){
//            shoppingCart = new ShoppingCart();
//            shoppingCart.setUser(userService.findById(userId));
//            shoppingCart = this.shoppingCartRepository.save(shoppingCart);
//        }
//        return shoppingCart;
//
//    }

    @Override
    public ShoppingCart cancelActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if(shoppingCart == null)
            throw new ShoppingCartIsNotActiveException();
        shoppingCart.setStatus(CartStatus.CANCELED);
        shoppingCart.setTimeCanceledOrFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart checkoutActiveShoppingCart(String userId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(userId, CartStatus.CREATED);
        if(shoppingCart == null)
            throw new ShoppingCartIsNotActiveException();

//        List<Book> books = shoppingCart.getBooks();
//        float price = 0;
//        for(Book book : books) {
//            if(book.getSample() <= 0)
//                throw new BookOutOfStockException(book.getId());
//            book.setSample(book.getSample()-1);
//            price+= book.getPrice();
//        }
//        shoppingCart.setBooks(books);


        //getAllCartItems
        //calculatePrice
        List<CartItem> cartItems = cartItemService.findAllByShoppingCartId(shoppingCart.getId());
        float price = 0;
        for(CartItem item : cartItems) {
            price+= item.getBook().getPrice();
            item.getBook().setSample(item.getBook().getSample()-1);
        }

        shoppingCart.setStatus(CartStatus.FINISHED);
        shoppingCart.setTimeCanceledOrFinished(LocalDateTime.now());
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
