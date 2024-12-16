package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.dto.category.CategoryDTO;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.response.CartResponse;
import com.example.front_end.model.response.CategoryResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.CategoryService;
import com.example.front_end.service.ProductService;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
    public class TestController {
    public static String errorMassage;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String getHome(Model model){
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (jwtFilter.getAccessToken() != null){

            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

        }

        List<CategoryDTO> categoryDTOS = categoryService.findAll();
        model.addAttribute("categoryDTOS", categoryDTOS);

        List<ProductResponse> productBySold = productService.get8ProductBySold();
        model.addAttribute("productBySold", productBySold);

        List<ProductResponse> productByDate = productService.get8ProductByDate();
        model.addAttribute("productByDate", productByDate);


        return "home";
    }

    @GetMapping("/admin/statistics")
    public String statistics(Model model){
        if (jwtFilter.getAccessToken() != null){

            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

        }


        return "statistics";
    }

    @GetMapping("/admin/user")
    public String getUser(Model model){
        if (jwtFilter.getAccessToken() != null){

            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

        }


        return "admin_user";
    }

    @GetMapping("/admin/home")
    public String getAdminHome(Model model){
        if (jwtFilter.getAccessToken() != null){

            Long id = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);

        }


        return "admin";
    }
}
