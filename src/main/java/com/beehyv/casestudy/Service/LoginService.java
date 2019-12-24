package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Repository.LoginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    LoginDetailsRepository loginDetailsRepository;
    public int loggedInUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return loginDetailsRepository.findByEmail(email).getId();
    }
}
