package com.beehyv.casestudy.Repository;

        import com.beehyv.casestudy.Entity.Product;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductId(int id);
    List<Product> findByCategory(String category);
    List<Product> findByProductNameContainingOrCategoryContainingOrSubCategoryContainingOrDetailsContaining(String productName, String category, String subCategory, String details);
    List<Product> findByProductName(String productName);
    List<Product> findByCategoryAndPriceBetween(String category, int minimum, int maximum);
}
