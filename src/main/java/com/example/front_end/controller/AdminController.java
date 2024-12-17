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
    @GetMapping("/home")
    public String getAllUsersSorted(Model model) {
        try {
            // Lấy danh sách người dùng sắp xếp từ mới nhất
            List<UserResponse> users = userService.getAllUsersSortedByRegistrationTime();
            model.addAttribute("users", users);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi lấy danh sách người dùng.");
        }
        return "admin";  // Trả về giao diện quản lý người dùng
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
    @GetMapping("/detailUsers/{id}")
    public String detailUser(@PathVariable Long id, Model model) {
        UserDTO user = userService.findUserById(id);  // Fetch the user by ID using the service
        model.addAttribute("user", user);
        return "detail_user";  // Return the edit user form page
    }
    @GetMapping("/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDTO());  // Create an empty CategoryDTO for the form
        return "add-category";  // Return the form to add a category
    }
    @PostMapping("/categories/add")
    public String addCategory(@RequestBody CategoryDTO categoryDTO, Model model) {
        try {
            // Gọi CategoryService để gửi yêu cầu thêm danh mục
            CategoryDTO createdCategory = categoryService.addCategory(categoryDTO);

            // Nếu thêm thành công, hiển thị thông báo và chuyển hướng đến trang danh mục
            model.addAttribute("message", "Danh mục đã được thêm thành công!");
            return "redirect:/admin/categories";
        } catch (Exception e) {
            // Nếu có lỗi, hiển thị thông báo lỗi
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm danh mục.");
            return "add-category";
        }
    }

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

    @GetMapping("categories/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        // Lấy thông tin danh mục cần chỉnh sửa
        CategoryDTO category = categoryService.categoryById(id);

        // Đưa dữ liệu vào model để truyền sang giao diện
        model.addAttribute("category", category);
        return "form-edit-category"; // Tên file HTML
    }
    @PostMapping("/categories/update/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @ModelAttribute CategoryDTO categoryDTO,
            Model model) {
        try {
            // Gọi CategoryService để cập nhật danh mục
            CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);

            // Kiểm tra nếu cập nhật thành công
            if (updatedCategory != null) {
                model.addAttribute("message", "Danh mục đã được cập nhật thành công!");
            } else {
                model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật danh mục.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật danh mục: " + e.getMessage());
        }

        // Chuyển hướng về trang danh sách danh mục
        return "redirect:/admin/categories";
    }


}
