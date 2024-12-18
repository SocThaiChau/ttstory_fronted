package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.ProductRequestUI;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.response.*;
import com.example.front_end.service.ProductService;
import com.example.front_end.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            Long id = jwtFilter.getAuthenticaResponse().getUserDTO().getId();

            UserDTO userDTO = userService.findUserById(id);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());

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
            Long idUser = jwtFilter.getAuthenticaResponse().getUserDTO().getId();

            UserDTO userDTO = userService.findUserById(idUser);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());
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
            Long idUser = jwtFilter.getAuthenticaResponse().getUserDTO().getId();

            UserDTO userDTO = userService.findUserById(idUser);
            model.addAttribute("user", userDTO);

            model.addAttribute("name", userDTO.getName());
            model.addAttribute("role", jwtFilter.getAuthenticaResponse().getRole().getRoles());
        }
        List<ProductResponse> products = userService.productFavorite();
        if(products != null){
            model.addAttribute("product", products);
        }

        return "favorite_product";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        // Hiển thị thông báo lỗi nếu có
        if (errorMassage != null) {
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }

        // Gọi service để tìm kiếm sản phẩm
        List<ProductResponse> products = productService.searchProducts(keyword);
        model.addAttribute("product", products);
        model.addAttribute("keyword", keyword);

        return "product_search"; // Trả về trang hiển thị kết quả tìm kiếm
    }
    @PostMapping("/addToFavorites/{productId}")
    public ResponseEntity<ResponseObject> addToFavorites(HttpServletRequest request, @PathVariable("productId") Long productId) {
        try {
            Long userId = jwtFilter.getAuthenticaResponse().getUserDTO().getId();

            // Gọi service để thêm sản phẩm vào danh sách yêu thích
            boolean isAdded = productService.addProductToFavorites(userId, productId);
            System.out.println("Dâta" + isAdded);
            if (isAdded) {
                return ResponseEntity.ok(new ResponseObject("SUCCESS", "Product added to favorites successfully!"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("ERROR", "Product could not be added to favorites."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("ERROR", "An error occurred: " + e.getMessage()));
        }
    }

}
