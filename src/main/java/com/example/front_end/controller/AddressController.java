package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.AddressRequestUI;
import com.example.front_end.model.response.AddressResponse;
import com.example.front_end.model.response.CartResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.AddressService;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/address")
public class
AddressController {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AddressService addressService;

    private String message;
    private String errorMessage;
    @PostMapping("/add")
    public String addAddress(@ModelAttribute AddressRequestUI addressRequestUI, RedirectAttributes redirectAttributes) {
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }

        String result = addressService.addAddress(addressRequestUI);
        if (result == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm địa chỉ thất bại");
        } else {
            redirectAttributes.addFlashAttribute("message", "Địa chỉ đã được thêm");
        }

        return "redirect:/profile/address";
    }

    @PostMapping("/delete")
    public String deleteAddress(@ModelAttribute AddressRequestUI addressRequestUI, RedirectAttributes redirectAttributes) {
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }
        String result = addressService.deleteAddress(addressRequestUI.getId());
        if(result == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Xóa địa chỉ thất bại");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Địa chỉ đã được xóa");
        }

        return "redirect:/profile/address";
    }

    @PostMapping("/update")
    public String updateAddress(@ModelAttribute AddressRequestUI addressRequestUI, RedirectAttributes redirectAttributes) {
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }

        String result = addressService.updateAddress(addressRequestUI);
        if(result == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật địa chỉ thất bại");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Địa chỉ đã được cập nhật");
        }

        return "redirect:/profile/address";
    }

 }
