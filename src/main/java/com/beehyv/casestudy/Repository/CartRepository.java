package com.beehyv.casestudy.Repository;

import com.beehyv.casestudy.Entity.Cart;
import com.beehyv.casestudy.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByProfile(Profile profile);
}
