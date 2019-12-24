package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Entity.Cart;
import com.beehyv.casestudy.Entity.CartItem;
import com.beehyv.casestudy.Entity.Product;
import com.beehyv.casestudy.Entity.Profile;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Repository.CartItemRepository;
import com.beehyv.casestudy.Repository.CartRepository;
import com.beehyv.casestudy.Repository.ProductRepository;
import com.beehyv.casestudy.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    LoginService loginService;
    public void addToCart(int userId, int productId) throws CartNotFoundException {
        if(userId == loginService.loggedInUser()) {
            CartItem cartItem = null;
            boolean flag = true;
            List<Profile> profiles = profileRepository.findByUserId(userId);
            Profile profile = profiles.get(0);
            Cart cart = cartRepository.findByProfile(profile);
            List<Product> products = productRepository.findByProductId(productId);
            Product product = products.get(0);
            List<CartItem> cartItems = cart.getCartItem();
            if (!cartItems.isEmpty())
                for (CartItem cartItemTemp : cartItems) {
                    if (cartItemTemp.getProduct().equals(product)) {
                        cartItem = cartItemTemp;
                        flag = false;
                        break;
                    }
                }
            if (flag) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItemRepository.save(cartItem);
                cartItems.add(cartItem);
                cart.setCartItem(cartItems);
                cart.setCartItem(cartItems);
                cartRepository.save(cart);
            }
            if (!flag) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItemRepository.save(cartItem);
            }
        }
        else
            throw new CartNotFoundException("Forbidden");
    }
    public Cart getCart(int userId) throws CartNotFoundException {
        if(userId == loginService.loggedInUser()) {
            List<Profile> profiles = profileRepository.findByUserId(userId);
            Profile profile = profiles.get(0);
            Cart cart = cartRepository.findByProfile(profile);
            List<CartItem> cartItems = cart.getCartItem();
            int cartItemId = 0;
            for (CartItem cartItemTemp : cartItems)
                cartItemTemp.setItemId(++cartItemId);
            cart.setCartItem(cartItems);
            return cart;
        }
        else
            throw new CartNotFoundException("Forbidden");
    }
    public CartItem getCartItem(int userId, int cartItemId) throws CartNotFoundException, CartItemNotFoundException {
        if(userId == loginService.loggedInUser()) {
            List<Profile> profiles = profileRepository.findByUserId(userId);
            Profile profile = profiles.get(0);
            Cart cart = cartRepository.findByProfile(profile);
            List<CartItem> cartItem1 = cart.getCartItem();
            if (cartItem1.isEmpty())
                throw new CartItemNotFoundException("empty cart");
            else {
                if(cartItemId > cartItem1.size()){
                    throw new IndexOutOfBoundsException("no cart item Found");
                }
                CartItem cartItem = cartItem1.get(cartItemId - 1);
                cartItem.setItemId(cartItemId);
                return cartItem;
            }
        }
        else
            throw new CartNotFoundException("Forbidden");
    }
    public String removeFromCart(int userId, int productId) throws CartNotFoundException, UserNotFoundException, ProductNotFoundException, CartItemNotFoundException {
        if(userId == loginService.loggedInUser()) {
            CartItem cartItem = getCartItemByProductId(userId, productId);
            if(cartItem == null){
                throw new CartItemNotFoundException("no cartItem Found");
            }
            else {
                if (cartItem.getQuantity() == 1) {
                    cartItemRepository.delete(cartItem);
                } else if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    cartItemRepository.save(cartItem);
                }
                Product product = cartItem.getProduct();
                return product.getProductName();
            }
        }
        else
            throw new CartNotFoundException("Forbidden");
    }
    public CartItem changeQuantity(int userId, int productId, int quantity) throws CartNotFoundException, UserNotFoundException, ProductNotFoundException, CartItemNotFoundException {
        if(userId == loginService.loggedInUser()) {
            CartItem cartItem = getCartItemByProductId(userId, productId);
            if(cartItem == null){
                throw new CartItemNotFoundException("no cartItem Found");
            }
            else {
                cartItem.setQuantity(quantity);
                cartItemRepository.save(cartItem);
                return cartItem;
            }
        }
        else
            throw new CartNotFoundException("Forbidden");
    }
    public CartItem getCartItemByProductId(int userId, int productId) throws CartNotFoundException, UserNotFoundException, ProductNotFoundException {
        if(userId == loginService.loggedInUser()) {
            CartItem cartItem = null;
            List<Profile> profiles = profileRepository.findByUserId(userId);
            if(profiles.isEmpty()){
                throw new UserNotFoundException("invalid userId");
            }
            else {
                Profile profile = profiles.get(0);
                Cart cart = cartRepository.findByProfile(profile);
                List<CartItem> cartItems = cart.getCartItem();
                List<Product> products = productRepository.findByProductId(productId);
                if(products.isEmpty()){
                    throw new ProductNotFoundException("invalid productId");
                }
                else {
                    Product product = products.get(0);
                    for (CartItem cartItemTemp : cartItems) {
                        if (cartItemTemp.getProduct().equals(product)) {
                            cartItem = cartItemTemp;
                            break;
                        }
                    }
                    return cartItem;
                }
            }
        }
        else
            throw new CartNotFoundException("Forbidden");
    }
}
