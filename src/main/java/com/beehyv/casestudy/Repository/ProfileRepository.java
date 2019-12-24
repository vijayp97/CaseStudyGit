package com.beehyv.casestudy.Repository;

import com.beehyv.casestudy.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    List<Profile> findByUserId(int id);
}
