package com.beehyv.casestudy.ServiceTest;

import com.beehyv.casestudy.Entity.*;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Repository.*;
import com.beehyv.casestudy.Service.CartService;
import com.beehyv.casestudy.Service.LoginService;
import com.beehyv.casestudy.Service.UserService;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartServiceTest {
    @MockBean
    ProfileRepository profileRepository;
    @MockBean
    ProductRepository productRepository;
    @MockBean
    CartItemRepository cartItemRepository;
    @MockBean
    CartRepository cartRepository;
    @MockBean
    LoginService loginService;
    @Autowired
    CartService cartService;
    @Test
    public void addToCartTest1() throws CartNotFoundException {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        cartService.addToCart(userId, productId);
        verify(cartItemRepository , times(1)).save(any(CartItem.class));
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
    @Test
    public void addToCartTest2() throws CartNotFoundException {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        cartService.addToCart(userId, productId);
        verify(cartItemRepository , times(1)).save(any(CartItem.class));
    }
    @Test
    public void addToCartTest3() {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            cartService.addToCart(userId, productId);
        }
        catch (CartNotFoundException e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void getCartTest1() throws CartNotFoundException {
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        assertEquals(cart, cartService.getCart(userId));
    }
    @Test
    public void getCartTest2(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            cartService.getCart(userId);
        }
        catch (CartNotFoundException e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void getCartItemTest1() throws CartNotFoundException, CartItemNotFoundException {
        int userId = 1;
        int cartItemId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        assertEquals(cartItem, cartService.getCartItem(userId, cartItemId));
    }
    @Test
    public void getCartItemTest2(){
        int userId = 1;
        int cartItemId = 2;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        try{
            cartService.getCartItem(userId, cartItemId);
        }
        catch(Exception e){
            assertEquals("no cart item Found", e.getMessage());
        }
    }
    @Test
    public void getCartItemTest3(){
        int userId = 1;
        int cartItemId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<>();
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        try{
            cartService.getCartItem(userId, cartItemId);
        }
        catch(Exception e){
            assertEquals("empty cart", e.getMessage());
        }
    }
    @Test
    public void getCartItemTest4(){
        int userId = 1;
        int cartItemId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            cartService.getCartItem(userId, cartItemId);
        }
        catch(Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void getCartItemByProductIdTest1() throws UserNotFoundException, CartNotFoundException, ProductNotFoundException {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        assertEquals(cartItem, cartService.getCartItemByProductId(userId, productId));
    }
    @Test
    public void getCartItemByProductIdTest2(){
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        when(productRepository.findByProductId(productId)).thenReturn(products);
        try{
            cartService.getCartItemByProductId(userId, productId);
        }
        catch(Exception e){
            assertEquals("invalid productId", e.getMessage());
        }
    }
    @Test
    public void getCartItemByProductIdTest3() {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        try{
            cartService.getCartItemByProductId(userId, productId);
        }
        catch(Exception e){
            assertEquals("invalid userId", e.getMessage());
        }
    }
    @Test
    public void getCartItemByProductIdTest4(){
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            cartService.getCartItemByProductId(userId, productId);
        }
        catch(Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void removeFromCartTest1() throws UserNotFoundException, CartNotFoundException, ProductNotFoundException, CartItemNotFoundException {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        cartService.removeFromCart(userId, productId);
        verify(cartItemRepository, times(1)).delete(any());
    }
    @Test
    public void removeFromCartTest2() throws UserNotFoundException, ProductNotFoundException, CartItemNotFoundException, CartNotFoundException {
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(3);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        cartService.removeFromCart(userId, productId);
        verify(cartItemRepository, times(1)).save(any());
    }
    @Test
    public void removeFromCartTest3(){
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        Product product = new Product();
        product.setProductId(1);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        try {
            cartService.removeFromCart(userId, productId);
        }
        catch (Exception e){
            assertEquals("no cartItem Found", e.getMessage());
        }
    }
    @Test
    public void removeFromCartTest4(){
        int userId = 1;
        int productId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try {
            cartService.removeFromCart(userId, productId);
        }
        catch (Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void changeQuantityTest1() throws UserNotFoundException, ProductNotFoundException, CartItemNotFoundException, CartNotFoundException {
        int userId = 1;
        int productId = 1;
        int quantity = 3;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(1);
        Product product = new Product();
        product.setProductId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        cartService.changeQuantity(userId, productId, quantity);
        verify(cartItemRepository, times(1)).save(any());
    }
    @Test
    public void changeQuantityTest2(){
        int userId = 1;
        int productId = 1;
        int quantity = 3;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        Cart cart = new Cart();
        cart.setProfile(profile);
        cart.setCartId(1);
        List<CartItem> cartItems = new LinkedList<CartItem>();
        Product product = new Product();
        product.setProductId(1);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        try {
            cartService.changeQuantity(userId, productId, quantity);
        }
        catch (Exception e){
            assertEquals("no cartItem Found", e.getMessage());
        }
    }
    @Test
    public void changeQuantityTest3(){
        int userId = 1;
        int productId = 1;
        int quantity = 3;
        when(loginService.loggedInUser()).thenReturn(2);
        try {
            cartService.changeQuantity(userId, productId, quantity);
        }
        catch (Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
}
