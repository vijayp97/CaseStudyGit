package com.beehyv.casestudy.ControllerTest;

import com.beehyv.casestudy.Controller.UserController;
import com.beehyv.casestudy.Exception.UserNotFoundException;
import com.beehyv.casestudy.Exception.UserPresentException;
import com.beehyv.casestudy.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    UserController userController;
    @Test
    public void addUserTest() throws UserPresentException, UserNotFoundException {
        userController.addUser(any());
        verify(userService, times(1)).addUser(any());
    }
    @Test
    public void getProfileByIdTest() throws UserNotFoundException {
        userController.getProfileById(anyInt());
        verify(userService, times(1)).getProfileById(anyInt());
    }
    @Test
    public void updateProfileTest() throws UserNotFoundException {
        userController.updateProfile(any());
        verify(userService, times(1)).updateProfile(any());
    }
}
