package com.beehyv.casestudy.Controller;

import com.beehyv.casestudy.Entity.Product;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/admin/products/addProduct")
    public List<Product> addProduct(Product product) throws ProductNotFoundException {
        productService.addProduct(product);
        return productService.getProductsByName(product.getProductName());
    }
    @GetMapping("/user/products/getById/{productId}")
    public Product getProductById(@PathVariable("productId") int id) throws ProductNotFoundException {
        return productService.getProductById(id).get(0);
    }
    @GetMapping("/user/products/{category}")
    public List<Product> getProductsByCategory(@PathVariable("category") String category) throws ProductNotFoundException {
        return productService.getProductsByCategory(category);
    }
    @GetMapping("/user/products/search/{searchString}")
    public List<Product> getProductsBySearchString(@PathVariable("searchString")String searchString) throws ProductNotFoundException {
        return productService.getProductBySearchString(searchString);
    }
    @PostMapping("/admin/products/update")
    public List<Product> updateProduct(Product product) throws ProductNotFoundException{
        productService.updateProduct(product);
        return productService.getProductById(product.getProductId());
    }
    @PostMapping("/user/products/{category}/getFilteredProducts")
    public List<Product> getFilteredProductsByCategory(@PathVariable("category") String category, int minimum, int maximum) throws ProductNotFoundException {
        return productService.getFilteredProductsByCategory(category, minimum, maximum);
    }
    @GetMapping("/user/products/getProducts")
    public List<Product> getProducts(){
        return productService.getProducts();
    }
    @PostMapping("/user/products/{subCategory}/getFilteredProducts")
    public List<Product> getFilteredProductsBySubCategory(@PathVariable("subCategory") String subCategory, int minimum, int maximum) throws ProductNotFoundException {
        return productService.getFilteredProductsBySubCategory(subCategory, minimum, maximum);
    }
    @PostMapping("/user/products/{details}/getFilteredProducts")
    public List<Product> getFilteredProductsByDetails(@PathVariable("details") String details, int minimum, int maximum) throws ProductNotFoundException {
        return productService.getFilteredProductsByDetails(details, minimum, maximum);
    }
    @PostMapping("/user/products/{productName}/getFilteredProducts")
    public List<Product> getFilteredProductsByProductName(@PathVariable("productName") String productName, int minimum, int maximum) throws ProductNotFoundException {
        return productService.getFilteredProductsByProductName(productName, minimum, maximum);
    }
}
