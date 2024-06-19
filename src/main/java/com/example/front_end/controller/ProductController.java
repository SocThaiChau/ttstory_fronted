package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.response.ImageResponse;
import com.example.front_end.model.response.ProducListResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    public static String errorMassage;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    @GetMapping
    public String product(Model model){
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
        List<ProductResponse> product = userService.findAllProduct();

        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model){
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (jwtFilter.getAccessToken() != null){
            Long idUser = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(idUser);
            model.addAttribute("user", userResponse);

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
        }
        ProductResponse products = userService.findProductById(id);
        if(products != null){
            model.addAttribute("product", products);
        }


        return "product_detail";
    }
}
