package com.beehyv.casestudy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/signUpPage")
    public String signUpPage(){
        return "signUp.html";
    }
    @RequestMapping("/login")
    public String loginPage(){
        return "loginPage.html";
    }
    @RequestMapping("/updateProfilePage")
    public String updateProfilePage(){
        return "updateProfile.html";
    }
    @RequestMapping("/addProductPage")
    public String addProductPage(){
        return "addProduct.html";
    }
    @RequestMapping("/updateProductPage")
    public String updateProductPage(){
        return "updateProduct.html";
    }
    @RequestMapping("/profilePage")
    public String profilePage(){
        return "profile.html";
    }
    @RequestMapping("/getProfilePage")
    public String getProfilePage(){
        return "getProfile.html";
    }
}
