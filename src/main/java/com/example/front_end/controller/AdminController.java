package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.response.CartResponse;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getAdminHome(Model model){
        if (jwtFilter.getAccessToken() != null){

            String role = jwtFilter.getAuthenticaResponse().getRole().getRoles();
            if (role.equals("ADMIN")){
                return "redirect:/admin/user";
            }
        }
        return "admin_login";
    }

    @PostMapping("/login")
    public String authenticate (@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model){
        userService.authenticate(username, password);
        return "redirect:/admin/";
    }
}
