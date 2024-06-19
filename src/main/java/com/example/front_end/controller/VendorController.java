package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendor")
public class VendorController {
    public static String errorMassage;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;
    @GetMapping
    public String getVendor(Model model){
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (jwtFilter.getAccessToken() != null){

            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
        }
        return "vendor_home";
    }
}
