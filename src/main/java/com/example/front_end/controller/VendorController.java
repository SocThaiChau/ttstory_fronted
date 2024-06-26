package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.ProductRequestUI;
import com.example.front_end.model.request.ProductRequest;
import com.example.front_end.model.response.CategoryResponse;
import com.example.front_end.model.response.OrderResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.UserResponse;
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
    @GetMapping
    public String getVendor(Model model){
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


            List<CategoryResponse> categoryResponse = userService.listCategory();
            model.addAttribute("categoryResponse", categoryResponse);

            List<ProductResponse> product = userService.findAllProduct();
            model.addAttribute("product", product);

            List<OrderResponse> orderResponses = userService.allOrderResponses();
            model.addAttribute("orderResponses",orderResponses);

        }
        return "vendor_home";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") Double price,
                             @RequestParam("promotionalPrice") Double promotionalPrice,
                             @RequestParam("quantity") Integer quantity,
                             @RequestParam("categoryId") Long categoryId,
                             @RequestPart("url") MultipartFile file, Model model) {
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }
        // Tạo đối tượng ProductRequest từ các tham số
        ProductRequestUI productRequestUI = new ProductRequestUI();
        productRequestUI.setName(name);
        productRequestUI.setDescription(description);
        productRequestUI.setPrice(price);
        productRequestUI.setPromotionalPrice(promotionalPrice);
        productRequestUI.setQuantity(quantity);
        productRequestUI.setCategoryId(categoryId);
        productRequestUI.setUrl(file.getOriginalFilename());

        String result = userService.addProduct(productRequestUI, file);
        if(result == null){
            errorMassage = "Thêm sản phẩm thất bại";
            model.addAttribute("errorMassage", errorMassage);
            System.out.println(errorMassage);
            return "redirect:/vendor";
        }
        else {
            massage = "Sản phẩn đã được thêm";
            model.addAttribute("massage", massage);
            System.out.println(massage);
        }

        return "redirect:/vendor";
    }

}
