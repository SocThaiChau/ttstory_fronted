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
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (jwtFilter.getAccessToken() != null){
            String name = jwtFilter.getAuthenticaResponse().getName();
            String role = jwtFilter.getAuthenticaResponse().getRole().getRoles();

            model.addAttribute("name", name);
            model.addAttribute("role", role);
        }
        return "home";
    }
}
