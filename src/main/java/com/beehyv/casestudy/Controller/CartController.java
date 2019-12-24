package com.beehyv.casestudy.Controller;

import com.beehyv.casestudy.Entity.Cart;
import com.beehyv.casestudy.Entity.CartItem;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping("/user/cart/{userId}/add/{productId}")
    public void addToCart(@PathVariable("userId") int userId, @PathVariable("productId") int productId) throws CartNotFoundException {
        cartService.addToCart(userId, productId);
    }
    @GetMapping("/user/cart/{userId}/getCart")
    public Cart getCart(@PathVariable("userId") int userId) throws CartNotFoundException {
        return cartService.getCart(userId);
    }
    @GetMapping("/user/cart/{userId}/getCartItem/{cartItemId}")
    public CartItem getCartItem(@PathVariable("userId") int userId, @PathVariable("cartItemId") int cartItemId) throws CartNotFoundException, CartItemNotFoundException {
        return cartService.getCartItem(userId, cartItemId);
    }
    @GetMapping("/user/cart/{userId}/remove/{productId}")
    public String removeFromCart(@PathVariable("userId") int userId, @PathVariable("productId") int productId) throws CartNotFoundException, UserNotFoundException, ProductNotFoundException, CartItemNotFoundException {
        String productName = cartService.removeFromCart(userId, productId);
        return String.format(productName+" removed from cart");
    }
    @PostMapping("/user/cart/{userId}/changeQuantity/{productId}")
    public CartItem changeQuantity(@PathVariable("userId") int userId, @PathVariable("productId") int productId, int quantity) throws CartNotFoundException, UserNotFoundException, ProductNotFoundException, CartItemNotFoundException {
        return cartService.changeQuantity(userId, productId, quantity);
    }
}
