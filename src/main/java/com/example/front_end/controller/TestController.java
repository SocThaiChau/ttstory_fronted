package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.response.CartResponse;
import com.example.front_end.model.response.CategoryResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
    public class TestController {
    public static String errorMassage;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public String getHome(Model model){
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

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

        }

        List<CategoryResponse> categoryResponse = userService.listCategory();
        model.addAttribute("categoryResponse", categoryResponse);

        List<ProductResponse> productBySold = userService.get8ProductBySold();
        model.addAttribute("productBySold", productBySold);

        List<ProductResponse> productByDate = userService.get8ProductByDate();
        model.addAttribute("productByDate", productByDate);


        return "home";
    }
}
