package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.ProductRequestUI;
import com.example.front_end.model.dto.category.CategoryDTO;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.request.ProductRequest;
import com.example.front_end.model.response.CategoryResponse;
import com.example.front_end.model.response.OrderResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.CategoryService;
import com.example.front_end.service.ProductService;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/vendor")
public class VendorController {
    public static String errorMassage;
    public static String massage;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public String getVendor(Model model){
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (jwtFilter.getAccessToken() != null){

            Long id = jwtFilter.getAuthenticaResponse().getUserDTO().getId();

            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());


            List<CategoryDTO> categoryDTOS = categoryService.findAll();
            model.addAttribute("categoryDTOS", categoryDTOS);

            List<ProductResponse> product = productService.getAllMyProducts();
            model.addAttribute("product", product);

            List<OrderResponse> orderResponses = userService.allOrderResponses();
            model.addAttribute("orderResponses",orderResponses);

        }
        return "vendor_home";
    }



}
