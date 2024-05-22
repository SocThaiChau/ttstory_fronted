package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
    public class TestController {
    public static String errorMassage;
    @Autowired
    private JwtFilter jwtFilter;

    @GetMapping("/home")
    public String getHome(Model model){
        if (jwtFilter.getAccessToken() == null){
            return "redirect:/login";
        }
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        String name = jwtFilter.getAuthenticaResponse().getName();
        model.addAttribute("name", name);
        return "home";
    }
}
