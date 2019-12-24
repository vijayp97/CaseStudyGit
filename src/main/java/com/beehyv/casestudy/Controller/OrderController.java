package com.beehyv.casestudy.Controller;

import com.beehyv.casestudy.Entity.Order;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.OrderNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/user/order/{userId}/createOrder")
    public Order createOrder(@PathVariable("userId") int userId) throws OrderNotFoundException, UserNotFoundException, CartItemNotFoundException {
        return orderService.createOrder(userId);
    }
    @GetMapping("/user/order/{userId}/getOrders")
    public List<Order> getOrders(@PathVariable("userId") int userId) throws OrderNotFoundException {
        return orderService.getOrders(userId);
    }
}
