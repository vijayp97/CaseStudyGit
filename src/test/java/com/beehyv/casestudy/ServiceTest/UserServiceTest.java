package com.beehyv.casestudy.ServiceTest;

import com.beehyv.casestudy.Entity.LoginDetails;
import com.beehyv.casestudy.Entity.Profile;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Exception.UserPresentException;
import com.beehyv.casestudy.Repository.CartRepository;
import com.beehyv.casestudy.Repository.LoginDetailsRepository;
import com.beehyv.casestudy.Repository.ProfileRepository;
import com.beehyv.casestudy.Service.LoginService;
import com.beehyv.casestudy.Service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @MockBean
    ProfileRepository profileRepository;
    @MockBean
    LoginDetailsRepository loginDetailsRepository;
    @MockBean
    CartRepository cartRepository;
    @MockBean
    LoginService loginService;
    @Test
    public void hashPasswordTest(){
        String password = "admin";
        assertTrue(BCrypt.checkpw(password, userService.hashPassword(password)));
    }
    @Test
    public void addUserTest1() throws UserPresentException, UserNotFoundException {
        LoginDetails loginDetails = new LoginDetails();
        LoginDetails loginDetails1 = null;
        loginDetails.setEmail("abc@abc");
        when(loginDetailsRepository.findByEmail(any())).thenReturn(loginDetails1);
        userService.addUser(loginDetails);
        verify(loginDetailsRepository, times(1)).save(any());
        verify(profileRepository, times(1)).save(any());
        verify(cartRepository, times(1)).save(any());
    }
    @Test
    public void addUserTest2(){
        LoginDetails loginDetails = new LoginDetails();
        LoginDetails loginDetails1 = new LoginDetails();
        loginDetails.setEmail("abc@abc");
        loginDetails1.setEmail("abc@abc");
        when(loginDetailsRepository.findByEmail(any())).thenReturn(loginDetails1);
        try {
            userService.addUser(loginDetails);
        }
        catch(Exception e){
            assertEquals("User Already Present", e.getMessage());
        }
    }
    @Test
    public void addUserTest3(){
        LoginDetails loginDetails = null;
        try {
            userService.addUser(loginDetails);
        }
        catch(Exception e){
            assertEquals("invalid signup details", e.getMessage());
        }
    }
    @Test
    public void getProfileByIdTest1() throws UserNotFoundException {
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        Profile profile = new Profile();
        profile.setUserId(1);
        List<Profile> profiles = new LinkedList<>();
        profiles.add(profile);
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        assertEquals(1, userService.getProfileById(userId).size());
    }
    @Test
    public void getProfileByIdTest2(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(1);
        List<Profile> profiles = new LinkedList<>();
        when(profileRepository.findByUserId(userId)).thenReturn(profiles);
        try{
            userService.getProfileById(userId);
        }
        catch(Exception e){
            assertEquals("user not Found", e.getMessage());
        }
    }
    @Test
    public void getProfileByIdTest3(){
        int userId = 1;
        when(loginService.loggedInUser()).thenReturn(2);
        try{
            userService.getProfileById(userId);
        }
        catch(Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
    @Test
    public void updateProfileTest1() throws UserNotFoundException {
        when(loginService.loggedInUser()).thenReturn(1);
        List<Profile> profiles = new LinkedList<>();
        Profile profile = new Profile();
        profile.setUserId(1);
        profiles.add(profile);
        when(profileRepository.findByUserId(anyInt())).thenReturn(profiles);
        userService.updateProfile(profile);
        verify(profileRepository, times(1)).save(any());
    }
    @Test
    public void updateProfileTest2(){
        when(loginService.loggedInUser()).thenReturn(1);
        List<Profile> profiles = new LinkedList<>();
        Profile profile = new Profile();
        profile.setUserId(1);
        when(profileRepository.findByUserId(anyInt())).thenReturn(profiles);
        try{
            userService.updateProfile(profile);
        }
        catch (Exception e){
            assertEquals("no users Found", e.getMessage());
        }
    }
    @Test
    public void updateProfileTest3(){
        when(loginService.loggedInUser()).thenReturn(2);
        Profile profile = new Profile();
        profile.setUserId(1);
        try{
            userService.updateProfile(profile);
        }
        catch (Exception e){
            assertEquals("Forbidden", e.getMessage());
        }
    }
}
