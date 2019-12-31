package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Entity.*;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.OrderNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    LoginService loginService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    public Order createOrder(int userId) throws OrderNotFoundException, UserNotFoundException, CartItemNotFoundException {
        if(userId == 0){
            userId = 1;
        }
        if(/*userId == loginService.loggedInUser()*/true) {
            Order order = new Order();
            order.setUser(userId);
            List<Profile> profiles = profileRepository.findByUserId(userId);
            if(profiles.isEmpty()){
                throw new UserNotFoundException("invalid userId");
            }
            else {
                Profile profile = profiles.get(0);
                Cart cart = cartRepository.findByProfile(profile);
                List<CartItem> cartItems = cart.getCartItem();
                List<OrderItem> orderItems = new LinkedList<>();
                if(cartItems.isEmpty()){
                    throw new CartItemNotFoundException("no cart items Found");
                }
                else {
                    for (CartItem cartItemTemp : cartItems) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setProduct(cartItemTemp.getProduct());
                        orderItem.setQuantity(cartItemTemp.getQuantity());
                        orderItems.add(orderItem);
                        orderItemRepository.save(orderItem);
                        cartItemRepository.delete(cartItemTemp);
                    }
                    order.setOrderItems(orderItems);
                    order.setOrderStatus("Order Placed");
                    orderRepository.save(order);
                    int orderItemId = 0;
                    for(OrderItem orderItemTemp : orderItems)
                        orderItemTemp.setOrderItemId(++orderItemId);
                    return order;
                }
            }
        }
        else
            throw new OrderNotFoundException("Forbidden");
    }
    public List<Order> getOrders(int userId) throws OrderNotFoundException {
        if(userId == 0){
            userId = 1;
        }
        if(/*userId == loginService.loggedInUser()*/true) {
            List<Order> orders = orderRepository.findByUser(userId);
            /*if(orders.isEmpty()){
                throw new OrderNotFoundException("no Orders");
            }*/
            int orderId = 0;
            for(Order orderTemp : orders){
                List<OrderItem> orderItems = orderTemp.getOrderItems();
                int orderItemId = 0;
                for(OrderItem orderItemTemp : orderItems)
                    orderItemTemp.setOrderItemId(++orderItemId);
                orderTemp.setOrderId(++orderId);
            }
            return orders;
        }
        else
            throw new OrderNotFoundException("Forbidden");
    }
}
