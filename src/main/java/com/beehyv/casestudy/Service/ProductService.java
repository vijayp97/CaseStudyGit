package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Entity.Product;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService{
    @Autowired
    ProductRepository productRepository;
    public void addProduct(Product product){
        productRepository.save(product);
    }
    public void updateProduct(Product product) throws ProductNotFoundException {
        List<Product> products = productRepository.findByProductId(product.getProductId());
        if(products.isEmpty()){
            throw new ProductNotFoundException("invalid productId");
        }
        productRepository.save(product);
    }
    public List<Product> getProductById(int id) throws ProductNotFoundException{
        List<Product> products = productRepository.findByProductId(id);
        if(products.isEmpty()){
            throw new ProductNotFoundException("not found");
        }
        return products;
    }
    public List<Product> getProductsByCategory(String category) throws ProductNotFoundException {
        List<Product> products = productRepository.findByCategory(category);
        if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }
        return products;
    }
    public List<Product> getProductBySearchString(String searchString) throws ProductNotFoundException {
        List<Product> products = productRepository.findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(searchString , searchString, searchString,searchString);
        if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }
        return products;
    }
    public List<Product> getProductsByName(String productName) throws ProductNotFoundException {
        List<Product> products = productRepository.findByProductName(productName);
        if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }
        return products;
    }
    public List<Product> getFilteredProducts(String category , int minimum, int maximum) throws ProductNotFoundException {
        List<Product> products = productRepository.findByCategoryAndPriceBetween(category, minimum, maximum);
        if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }
        return products;
    }
}
