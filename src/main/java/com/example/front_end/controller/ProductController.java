package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.ProductRequestUI;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.response.*;
import com.example.front_end.service.ProductService;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    public static String errorMassage;
    public static String massage;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String product(Model model){
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
//            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());

            CartResponse cartResponse = userService.cartDetail();
            Integer total = cartResponse.getTotalItem();
            model.addAttribute("total", total);
        }
        List<ProductResponse> product = productService.findAllProduct();
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
            UserDTO userDTO = userService.findUserById(idUser);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
//            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
        }
        ProductResponse products = productService.findProductById(id);
        if(products != null){
            Long userId = products.getUserId();
            String userName = products.getCreatedBy();
            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);
            model.addAttribute("product", products);
        }

        return "product_detail";
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

        String result = productService.addProduct(productRequestUI, file);
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
    @GetMapping("/favorite")
    public String productFavorite(Model model){
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        if (jwtFilter.getAccessToken() != null){
            Long idUser = jwtFilter.getAuthenticaResponse().getUserResponse().getId();
            UserDTO userDTO = userService.findUserById(idUser);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
//            model.addAttribute("role", userResponse.getUserRoleResponse().getRoles());
        }
        List<ProductResponse> products = userService.productFavorite();
        if(products != null){
            model.addAttribute("product", products);
        }

        return "favorite_product";
    }
}
