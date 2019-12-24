package com.beehyv.casestudy.Controller;

import com.beehyv.casestudy.Entity.Address;
import com.beehyv.casestudy.Entity.Cart;
import com.beehyv.casestudy.Entity.LoginDetails;
import com.beehyv.casestudy.Entity.Profile;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Exception.UserPresentException;
import com.beehyv.casestudy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public int addUser(LoginDetails loginDetails) throws UserPresentException, UserNotFoundException {
        return userService.addUser(loginDetails);
    }
    @GetMapping("/user/getprofile/{id}")
    public List<Profile> getProfileById(@PathVariable("id") int id) throws UserNotFoundException {
        return userService.getProfileById(id);
    }
    @PostMapping("/user/updateProfile")
    public void updateProfile(Profile profile) throws UserNotFoundException {
        userService.updateProfile(profile);
    }
}
