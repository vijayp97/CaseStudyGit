package com.beehyv.casestudy.ControllerTest;

import com.beehyv.casestudy.Controller.CartController;
import com.beehyv.casestudy.Exception.CartItemNotFoundException;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Service.CartService;
import com.sun.xml.internal.ws.resources.UtilMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CartControllerTest {
    @MockBean
    CartService cartService;
    @Autowired
    CartController cartController;
    @Test
    public void addToCartTest() throws CartNotFoundException {
        cartController.addToCart(anyInt(), anyInt());
        verify(cartService, times(1)).addToCart(anyInt(), anyInt());
    }
    @Test
    public void getCartTest() throws CartNotFoundException {
        cartController.getCart(anyInt());
        verify(cartService, times(1)).getCart(anyInt());
    }
    @Test
    public void getCartItemTest() throws CartNotFoundException, CartItemNotFoundException {
        cartController.getCartItem(anyInt(), anyInt());
        verify(cartService, times(1)).getCartItem(anyInt(), anyInt());
    }
    @Test
    public void removeFromCartTest() throws CartNotFoundException, UserNotFoundException, CartItemNotFoundException, ProductNotFoundException {
        cartController.removeFromCart(anyInt(), anyInt());
        verify(cartService, times(1)).removeFromCart(anyInt(), anyInt());
    }
    @Test
    public void changeQuantityTest() throws CartNotFoundException, UserNotFoundException, CartItemNotFoundException, ProductNotFoundException {
        cartController.changeQuantity(anyInt(), anyInt(), anyInt());
        verify(cartService, times(1)).changeQuantity(anyInt(), anyInt(), anyInt());
    }
}
