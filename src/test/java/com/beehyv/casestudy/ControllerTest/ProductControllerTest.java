package com.beehyv.casestudy.ControllerTest;

import com.beehyv.casestudy.Controller.ProductController;
import com.beehyv.casestudy.Entity.Product;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductControllerTest {
    @MockBean
    ProductService productService;
    @Autowired
    ProductController productController;
    @Test
    public void addProductTest() throws ProductNotFoundException {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("abc");
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productService.getProductsByName(any())).thenReturn(products);
        productController.addProduct(product);
        verify(productService, times(1)).addProduct(any());
        assertEquals(1 , productController.addProduct(product).size());
    }
    @Test
    public void getProductByIdTest() throws ProductNotFoundException {
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productService.getProductById(anyInt())).thenReturn(products);
        assertEquals(product, productController.getProductById(anyInt()));
    }
    @Test
    public void getProductByCategoryTest() throws ProductNotFoundException {
        productController.getProductsByCategory(any());
        verify(productService, times(1)).getProductsByCategory(any());
    }
    @Test
    public void getProductBySearchStringTest() throws ProductNotFoundException {
        productController.getProductsBySearchString(any());
        verify(productService, times(1)).getProductBySearchString(any());
    }
    @Test
    public void updateProductTest() throws ProductNotFoundException {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("abc");
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productService.getProductById(anyInt())).thenReturn(products);
        productController.updateProduct(product);
        verify(productService, times(1)).updateProduct(any());
        assertEquals(1 , productController.updateProduct(product).size());
    }
    @Test
    public void getFilteredProductsTest() throws ProductNotFoundException {
        productController.getFilteredProductsByCategory(any(), anyInt(), anyInt());
        verify(productService, times(1)).getFilteredProductsByCategory(any(), anyInt(), anyInt());
    }
}
