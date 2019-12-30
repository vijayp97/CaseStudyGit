package com.beehyv.casestudy.Service;

import com.beehyv.casestudy.Entity.Cart;
import com.beehyv.casestudy.Entity.LoginDetails;
import com.beehyv.casestudy.Entity.Profile;
import com.beehyv.casestudy.Exception.CartNotFoundException;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Exception.UserPresentException;
import com.beehyv.casestudy.Repository.CartRepository;
import com.beehyv.casestudy.Repository.LoginDetailsRepository;
import com.beehyv.casestudy.Repository.ProfileRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    LoginDetailsRepository loginDetailsRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    LoginService loginService;
    public int addUser(LoginDetails loginDetails) throws UserPresentException, UserNotFoundException {
        if(loginDetails == null){
            throw new UserNotFoundException("invalid signup details");
        }
        else {
            int userId = 0;
            String email = loginDetails.getEmail();
            LoginDetails loginDetails1 = loginDetailsRepository.findByEmail(email);
            if (loginDetails1 == null) {
                String password = loginDetails.getPassword();
                loginDetails.setPassword(hashPassword(password));
                Profile profile = new Profile();
                profile.setName(loginDetails.getName());
                profile.setEmail(loginDetails.getEmail());
                loginDetails.setProfile(profile);
                loginDetails.setRole("user");
                loginDetails.setActive(true);
                loginDetailsRepository.save(loginDetails);
                profileRepository.save(profile);
                Cart cart = new Cart();
                cart.setProfile(profile);
                cartRepository.save(cart);
                userId = loginDetails.getId();
            }
            else
                throw new UserPresentException("User Already Present");
            return userId;
        }
    }
    public List<Profile> getProfileById(int id) throws UserNotFoundException {
        if(id == 0){
            id = 1;
        }
        if(/*id == loginService.loggedInUser()*/true) {
            List<Profile> profiles =  profileRepository.findByUserId(id);
            if(profiles.isEmpty()){
                throw new UserNotFoundException("user not Found");
            }
            return profiles;
        }
        else
            throw new UserNotFoundException("Forbidden");
    }
    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public void updateProfile(Profile profile) throws UserNotFoundException {
        if(/*profile.getUserId() == loginService.loggedInUser()*/true) {
            List<Profile> profiles = profileRepository.findByUserId(profile.getUserId());
            if(profiles.isEmpty()){
                throw new UserNotFoundException("no users Found");
            }
            profileRepository.save(profile);
        }
        else
            throw new UserNotFoundException("Forbidden");
    }
}
