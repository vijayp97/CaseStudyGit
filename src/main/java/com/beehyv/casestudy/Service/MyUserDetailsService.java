package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Configuration.UserPrincipal;
import com.beehyv.casestudy.Entity.LoginDetails;
import com.beehyv.casestudy.Repository.LoginDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    LoginDetailsRepository loginDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginDetails loginDetails = loginDetailsRepository.findByEmail(email);
        if(loginDetails == null){
            throw new UsernameNotFoundException("invalid credentials");
        }
        return new UserPrincipal(loginDetails);
    }
}
