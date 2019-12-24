package com.beehyv.casestudy.ServiceTest;

import com.beehyv.casestudy.Entity.*;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.OrderNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Repository.*;
import com.beehyv.casestudy.Service.LoginService;
import com.beehyv.casestudy.Service.OrderService;
import com.beehyv.casestudy.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @MockBean
    LoginService loginService;
    @MockBean
    OrderRepository orderRepository;
    @MockBean
    ProfileRepository profileRepository;
    @MockBean
    CartRepository cartRepository;
    @MockBean
    OrderItemRepository orderItemRepository;
    @MockBean
    CartItemRepository cartItemRepository;
    @Test
    public void createOrderTest1() throws UserNotFoundException, OrderNotFoundException, CartItemNotFoundException {
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
        CartItem cartItem = new CartItem();
        Product product = new Product();
        product.setProductId(1);
        cartItem.setItemId(1);
        cartItem.setProduct(product);
        cartItem.setQuantity(2);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        when(cartRepository.findByProfile(profile)).thenReturn(cart);
        orderService.createOrder(userId);
        verify(orderItemRepository, times(1)).save(any());
        verify(cartItemRepository, times(1)).delete(any());
        verify(orderRepository, times(1)).save(any());
    }
    @Test
    public void createOrderTest2(){
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
        try{
            orderService.createOrder(userId);
        }
        catch (Exception e){
            assertEquals("no cart items Found", e.getMessage());
        }
    }
    @Test
    public void createOrderTest3(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        List<Profile> profiles = new LinkedList<>();
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        try{
            orderService.createOrder(userId);
        }
        catch (Exception e){
            assertEquals("invalid userId", e.getMessage());
        }
    }
    @Test
    public void createOrderTest4(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            orderService.createOrder(userId);
        }
        catch (Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void getOrdersTest1() throws OrderNotFoundException {
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        List<Order> orders = new LinkedList<>();
        Order order = new Order();
        order.setOrderId(1);
        order.setUser(1);
        Product product = new Product();
        product.setProductId(1);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(1);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        List<OrderItem> orderItems = new LinkedList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
        orders.add(order);
        when(orderRepository.findByUser(userId)).thenReturn(orders);
        assertEquals(1, orderService.getOrders(userId).size());
    }
    @Test
    public void getOrdersTest2(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        List<Order> orders = new LinkedList<>();
        when(orderRepository.findByUser(userId)).thenReturn(orders);
        try{
            orderService.getOrders(userId);
        }
        catch (Exception e){
            assertEquals("no Orders", e.getMessage());
        }
    }
    @Test
    public void getOrdersTest3(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            orderService.getOrders(userId);
        }
        catch (Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
}
