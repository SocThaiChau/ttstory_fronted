package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.dto.category.CategoryDTO;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.request.UserRequest;
import com.example.front_end.model.response.PagedResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.CategoryService;
import com.example.front_end.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public static String errorMessage;
    public static String message;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @GetMapping("/categories")
    public String getCategoryPage(
            @RequestParam(defaultValue = "0") int page, // Trang hiện tại
            Model model) {

        int size = 7; // Số lượng phần tử trên mỗi trang
        PagedResponse<CategoryDTO> pagedCategories = categoryService.findAllWithPagination(page, size);

        if (pagedCategories != null) {
            model.addAttribute("categories", pagedCategories.getContent());
            model.addAttribute("currentPage", pagedCategories.getPageNumber());
            model.addAttribute("totalPages", pagedCategories.getTotalPages());
        }

        return "categories";
    }
    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        try {
            // Gọi CategoryService để gửi yêu cầu thêm danh mục
            CategoryDTO createdCategory = categoryService.addCategory(categoryDTO);

            // Nếu thêm thành công, hiển thị thông báo và chuyển hướng đến trang danh mục
            model.addAttribute("message", "Danh mục đã được thêm thành công!");
            return "redirect:/admin/categories";
        } catch (Exception e) {
            // Nếu có lỗi, hiển thị thông báo lỗi
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm danh mục.");
            return "categories";
        }
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        try {
            // Gọi service để lấy danh sách người dùng
            List<UserResponse> users = userService.getAllUsers();
            model.addAttribute("users", users);  // Thêm dữ liệu người dùng vào model
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi lấy danh sách người dùng.");
        }
        return "admin_user";  // Trả về view hiển thị người dùng
    }
    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        UserDTO user = userService.findUserById(id);  // Fetch the user by ID using the service
        model.addAttribute("user", user);
        return "form_edit_user";  // Return the edit user form page
    }
    @PostMapping("/saveUser/{id}")
    public String saveUser(@PathVariable Long id, @ModelAttribute UserDTO user, Model model) {
        try {
            // Gọi service để cập nhật user
            UserResponse updatedUser = userService.AdminUpdateUser(id, user);

            if (updatedUser != null) {
                model.addAttribute("message", "Thông tin người dùng đã được cập nhật thành công.");
            } else {
                model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật thông tin người dùng.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật người dùng.");
        }
        return "redirect:/admin/users";  // Quay lại trang danh sách người dùng
    }


}
