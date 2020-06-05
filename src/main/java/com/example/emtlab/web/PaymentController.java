package com.example.emtlab.web;

import com.example.emtlab.business.AuthService;
import com.example.emtlab.business.CartItemService;
import com.example.emtlab.business.ShoppingCartService;
import com.example.emtlab.model.Book;
import com.example.emtlab.model.CartItem;
import com.example.emtlab.model.ChargeRequest;
import com.example.emtlab.model.ShoppingCart;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Value("${STRIPE_P_KEY}")
    private String publicKey;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/charge")
    @Transactional
    public String getCheckoutPage(Model model){
        try{
            ShoppingCart shoppingCart = this.shoppingCartService.findActiveShoppingCartByUsername(this.authService.getCurrentUserId());
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("currency", "eur");
//            List<CartItem> cartItems = cartItemService.findAllByShoppingCartId(shoppingCart.getId());
            model.addAttribute("amount", (int)(shoppingCart.getCartItems().stream().mapToDouble(cartItem -> cartItem.getBook().getPrice()).sum()*100));
            model.addAttribute("stripePublicKey", publicKey);

            return "checkout";
        }
        catch (RuntimeException ex){
            return "redirect:/books?error=" + ex.getLocalizedMessage();
        }
    }

    @PostMapping("/charge")
    @Transactional
    public String checkout(ChargeRequest chargeRequest, Model model){
        try{
            ShoppingCart shoppingCart = this.shoppingCartService.checkoutActiveShoppingCart(authService.getCurrentUserId(), chargeRequest);
            return "redirect:/books?message=Successful payment";
        } catch (RuntimeException e) {
            return "redirect:/payments/charge?error=" + e.getLocalizedMessage();
        }
    }
}
