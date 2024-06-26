package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.response.*;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    private UserService userService;
    @GetMapping("/info")
    public String profile(Model model){
//        if(message != null){
//            model.addAttribute("message", message);
//        }
//        if(errorMessage != null){
//            model.addAttribute("errorMessage", errorMessage);
//        }
//        message = null;
//        errorMessage = null;

        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);
            return "ui_info";
        }
        return "redirect:/home";
    }

    @GetMapping("/order")
    public String profileOrder(Model model){
//        if(message != null){
//            model.addAttribute("message", message);
//        }
//        if(errorMessage != null){
//            model.addAttribute("errorMessage", errorMessage);
//        }
//        message = null;
//        errorMessage = null;

        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

            List<OrderResponse> orderResponses = userService.orderResponses();
            model.addAttribute("orderResponses",orderResponses);
            return "ui_profile_order";
        }
        return "redirect:/home";
    }

    @GetMapping("/password")
    public String profilePassword(Model model){
//        if(message != null){
//            model.addAttribute("message", message);
//        }
//        if(errorMessage != null){
//            model.addAttribute("errorMessage", errorMessage);
//        }
//        message = null;
//        errorMessage = null;

        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);
            return "ui_profile_password";
        }
        return "redirect:/home";
    }

    @GetMapping("/address")
    public String address(Model model){

        if (jwtFilter.getAccessToken() != null){
            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserResponse userResponse = userService.findUserById(id);
            model.addAttribute("user", userResponse);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(userResponse.getDob());

            model.addAttribute("name", userResponse.getName());
            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
            model.addAttribute("dob", dob);

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

            List<AddressResponse> addressResponses = userService.listAddress();
            model.addAttribute("addressResponses", addressResponses);

            // message và errorMessage được thêm vào từ RedirectAttributes
            if (model.containsAttribute("message")) {
                model.addAttribute("message", model.getAttribute("message"));
            }
            if (model.containsAttribute("errorMessage")) {
                model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
            }


            return "ui_profile_address";
        }
        return "redirect:/home";
    }
}
