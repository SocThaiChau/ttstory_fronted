package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Home {
    @Autowired
    private JwtFilter jwtFilter;
    @GetMapping
    public String login(){
        return "redirect:/home";
    }
}
