package com.beehyv.casestudy.ServiceTest;

import com.beehyv.casestudy.Entity.Product;
import com.beehyv.casestudy.Entity.Profile;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Repository.ProductRepository;
import com.beehyv.casestudy.Service.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Test
    public void addProductTest(){
        Product product = new Product();
        productService.addProduct(product);
        verify(productRepository, times(1)).save(product);
    }
    @Test
    public void updateProductTestNoException() throws ProductNotFoundException {
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(anyInt())).thenReturn(products);
        productService.updateProduct(product);
        verify(productRepository, times(1)).save(product);
    }
    @Test
    public void updateProductTestException(){
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new LinkedList<>();
        try{
            productService.updateProduct(product);
        }
        catch(Exception e){
            assertEquals("invalid productId", e.getMessage());
        }
    }
    @Test
    public void getProductByIdTestNoException() throws ProductNotFoundException {
        int productId = 1;
        Product product = new Product();
        product.setProductId(1);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductId(productId)).thenReturn(products);
        assertEquals(1, productService.getProductById(productId).size());
    }
    @Test
    public void getProductByIdTestException(){
        int productId = 1;
        List<Product> products = new LinkedList<>();
        when(productRepository.findByProductId(productId)).thenReturn(products);
        try{
            productService.getProductById(productId);
        }
        catch (ProductNotFoundException e){
            assertEquals("not found", e.getMessage());
        }
    }
    @Test
    public void getProductByCategoryTestNoException() throws ProductNotFoundException {
        String category = "mobile";
        Product product = new Product();
        product.setProductId(1);
        product.setCategory("mobile");
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByCategory(category)).thenReturn(products);
        assertEquals(1, productService.getProductsByCategory(category).size());
    }
    @Test
    public void getProductByCategoryTestException(){
        String category = "mobile";
        List<Product> products = new LinkedList<>();
        when(productRepository.findByCategory(category)).thenReturn(products);
        try{
            productService.getProductsByCategory(category);
        }
        catch (ProductNotFoundException e){
            assertEquals("no products found", e.getMessage());
        }
    }
    @Test
    public void getProductBySearchStringTestNoException() throws ProductNotFoundException {
        String searchString = "food";
        Product product = new Product();
        product.setProductId(1);
        product.setCategory("grains");
        product.setSubCategory("food");
        product.setProductName("rice");
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(searchString , searchString, searchString,searchString)).thenReturn(products);
        assertEquals(1, productService.getProductBySearchString(searchString).size());
    }
    @Test
    public void getProductBySearchStringTestException(){
        String searchString = "food";
        List<Product> products = new LinkedList<>();
        when(productRepository.findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(searchString , searchString, searchString,searchString)).thenReturn(products);
        try{
            productService.getProductBySearchString(searchString);
        }
        catch (ProductNotFoundException e){
            assertEquals("no products found", e.getMessage());
        }
    }
    @Test
    public void getProductByProductNameTestNoException() throws ProductNotFoundException {
        String productName = "rice";
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("rice");
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByProductName(productName)).thenReturn(products);
        assertEquals(1, productService.getProductsByName(productName).size());
    }
    @Test
    public void getProductByProductNameTestException(){
        String productName = "rice";
        List<Product> products = new LinkedList<>();
        when(productRepository.findByProductName(productName)).thenReturn(products);
        try{
            productService.getProductsByName(productName);
        }
        catch (ProductNotFoundException e){
            assertEquals("no products found", e.getMessage());
        }
    }
    @Test
    public void getFilteredProductsTestNoException() throws ProductNotFoundException {
        String category = "food";
        Product product = new Product();
        product.setProductId(1);
        product.setCategory("grains");
        product.setSubCategory("food");
        product.setProductName("rice");
        product.setPrice(39);
        List<Product> products = new LinkedList<>();
        products.add(product);
        when(productRepository.findByCategoryContainingAndPriceBetween(category, 30, 40)).thenReturn(products);
        assertEquals(1, productService.getFilteredProductsByCategory(category, 30, 40).size());
    }
    @Test
    public void getFilteredProductsTestException(){
        String category = "food";
        List<Product> products = new LinkedList<>();
        when(productRepository.findByCategoryContainingAndPriceBetween(category, 30, 40)).thenReturn(products);
        try{
            productService.getFilteredProductsByCategory(category, 30, 40);
        }
        catch (ProductNotFoundException e){
            assertEquals("no products found", e.getMessage());
        }
    }
}
