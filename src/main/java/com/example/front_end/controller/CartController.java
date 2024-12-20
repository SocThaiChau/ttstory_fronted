package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.response.CartResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    public static String errorMassage;
    public static String massage;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;
    @GetMapping
    public String cart(Model model){
        if(jwtFilter.getAccessToken() == null){
            return "redirect:/home";
        }
        if (errorMassage != null){
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (massage != null){
            model.addAttribute("massage", massage);
            massage = null;
        }
        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
//            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
        }

        CartResponse cartResponse = userService.cartDetail();
        model.addAttribute("cartResponse", cartResponse);
        Integer total = cartResponse.getTotalItem();
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@ModelAttribute AddToCartRequestUI addToCartRequestUI, Model model) {
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }
        String result = userService.addCart(addToCartRequestUI);
        if(result == null){
            errorMassage = "Thêm vào giỏ hàng thất bại";
            model.addAttribute("errorMassage", errorMassage);
            System.out.println(errorMassage);
            return "redirect:/home";
        }
        else {
            massage = "Sản phẩn đã được thêm";
            model.addAttribute("massage", massage);
            System.out.println(massage);
        }

        return "redirect:/cart";
    }
}
