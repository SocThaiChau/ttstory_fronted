package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cartItem")
public class CartItemController {
    public static String errorMassage;
    public static String massage;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id, Model model){
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }

        String result = userService.deleteCartItem(id);
        if(result == null){
            errorMassage = "Xoá sản phẩm thất bại";
            model.addAttribute("errorMassage", errorMassage);
            System.out.println(errorMassage);
        }
        else {
            massage = "Xóa sản phẩm thành công";
            model.addAttribute("massage", massage);
            System.out.println(massage);
        }

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("id") int id, @RequestParam("quantity") int quantity, Model model) {
        // Call the service to update the cart item quantity
        String result = userService.updateCartItem(id, quantity);

        if (result == null) {
            String errorMessage = "Cập nhật sản phẩm thất bại";
            model.addAttribute("errorMessage", errorMessage);
            System.out.println(errorMessage);
        } else {
            String message = "Cập nhật sản phẩm thành công";
            model.addAttribute("message", message);
            System.out.println(message);
        }

        return "redirect:/cart";
    }
}
