package com.beehyv.casestudy.Repository;

        import com.beehyv.casestudy.Entity.Product;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.security.core.parameters.P;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductId(int id);
    List<Product> findByCategory(String category);
    List<Product> findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(String productName, String category, String subCategory, String details);
    List<Product> findByProductName(String productName);
    List<Product> findByCategoryContainingAndPriceBetween(String category, int minimum, int maximum);
    List<Product> findBySubCategoryContainingAndPriceBetween(String subCategory, int minimum, int maximum);
    List<Product> findByDetailsContainingAndPriceBetween(String details, int minimum, int maximum);
    List<Product> findByProductNameContainingAndPriceBetween(String productName, int minimum, int maximum);
    List<Product> findAll();
}
