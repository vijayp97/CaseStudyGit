package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Entity.Product;
import com.beehyv.casestudy.Exception.ProductNotFoundException;
import com.beehyv.casestudy.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
        List<Product> products = productRepository.findByCategoryContaining(category);
        if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }
        return products;
    }
    public List<Product> getProductBySearchString(String searchString) throws ProductNotFoundException {
        List<Product> products = productRepository.findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(searchString , searchString, searchString,searchString);
        return products;
    }
    public List<Product> getFilteredProductBySearchString(String searchString, int minimum, int maximum) throws ProductNotFoundException {
        List<Product> products1 = productRepository.findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(searchString , searchString, searchString,searchString);
        List<Product> products2 = productRepository.findByPriceBetween(minimum, maximum);
        List<Product> products = new LinkedList<>();
        for (Product productTemp : products1){
            if(products2.contains(productTemp))
                products.add(productTemp);
        }
        return products;
    }
    public List<Product> getProductsByName(String productName) throws ProductNotFoundException {
        List<Product> products = productRepository.findByProductNameContaining(productName);
        if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }
        return products;
    }
    public List<Product> getFilteredProductsByCategory(String category , int minimum, int maximum) throws ProductNotFoundException {
        List<Product> products;
        if((minimum == 0)&&(maximum == 0))
            products = productRepository.findByCategoryContaining(category);
        else
            products = productRepository.findByCategoryContainingAndPriceBetween(category, minimum, maximum);
        /*if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }*/
        return products;
    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public List<Product> getFilteredProductsBySubCategory(String subCategory , int minimum, int maximum) throws ProductNotFoundException {
        List<Product> products;
        if((minimum == 0)&&(maximum == 0))
            products = productRepository.findBySubCategoryContaining(subCategory);
        else
            products = productRepository.findBySubCategoryContainingAndPriceBetween(subCategory, minimum, maximum);
        /*if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }*/
        return products;
    }
    public List<Product> getFilteredProductsByDetails(String details , int minimum, int maximum) throws ProductNotFoundException {
        List<Product> products;
        if((minimum == 0)&&(maximum == 0))
            products = productRepository.findByDetailsContaining(details);
        else
            products = productRepository.findByDetailsContainingAndPriceBetween(details, minimum, maximum);
        /*if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }*/
        return products;
    }
    public List<Product> getFilteredProductsByProductName(String productName , int minimum, int maximum) throws ProductNotFoundException {
        List<Product> products;
        if((minimum == 0)&&(maximum == 0))
            products = productRepository.findByProductNameContaining(productName);
        else
            products = productRepository.findByProductNameContainingAndPriceBetween(productName, minimum, maximum);
        /*if(products.isEmpty()){
            throw new ProductNotFoundException("no products found");
        }*/
        return products;
    }
}
