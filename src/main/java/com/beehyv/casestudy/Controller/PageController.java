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
    @RequestMapping("/profilePage")
    public String profilePage(){
        return "profilePage.html";
    }
    @RequestMapping("/adminPage")
    public String adminPage(){
        return "adminPage.html";
    }
    @RequestMapping("/homePage")
    public String homePage(){
        return "userHomePage.html";
    }
    @RequestMapping("/cartPage")
    public String cartPage(){
        return "cartPage.html";
    }
    @RequestMapping("/orderPage")
    public String orderPage(){
        return "orderPage.html";
    }
}
