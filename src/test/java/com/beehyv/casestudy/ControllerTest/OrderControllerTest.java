package com.beehyv.casestudy.ControllerTest;

import com.beehyv.casestudy.Controller.OrderController;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.OrderNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderControllerTest {
    @MockBean
    OrderService orderService;
    @Autowired
    OrderController orderController;
    @Test
    public void createOrderTest() throws UserNotFoundException, OrderNotFoundException, CartItemNotFoundException {
        orderController.createOrder(anyInt());
        verify(orderService, times(1)).createOrder(anyInt());
    }
    @Test
    public void getOrdersTest() throws OrderNotFoundException {
        orderController.getOrders(anyInt());
        verify(orderService, times(1)).getOrders(anyInt());
    }
}
