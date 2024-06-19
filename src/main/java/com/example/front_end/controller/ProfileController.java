package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    private UserService userService;
    @GetMapping("/info")
    public String profile(Model model){
        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);
            return "ui_info";
        }
        return "redirect:/home";
    }

    @GetMapping("/order")
    public String profileOrder(Model model){
        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);
            return "ui_profile_order";
        }
        return "redirect:/home";
    }

    @GetMapping("/password")
    public String profilePassword(Model model){
        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);
            return "ui_profile_password";
        }
        return "redirect:/home";
    }
}
