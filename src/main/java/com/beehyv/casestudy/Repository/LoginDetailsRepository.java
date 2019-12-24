package com.beehyv.casestudy.Repository;

import com.beehyv.casestudy.Entity.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginDetailsRepository extends JpaRepository<LoginDetails, Integer> {
    LoginDetails findByName(String name);
    LoginDetails findByEmail(String email);
}
