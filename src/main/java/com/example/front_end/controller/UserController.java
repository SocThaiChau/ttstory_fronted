package com.example.front_end.controller;

import com.example.front_end.model.UI.UserRequestUI;
import com.example.front_end.model.response.AuthenticaResponse;
import com.example.front_end.model.response.UserResponse;
import com.example.front_end.service.InputService;
import com.example.front_end.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.front_end.config.JwtFilter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private InputService inputService;

    private String message = null;
    private String errorMessage = null;


    @GetMapping("/getList")
    public String getAllUser(Model model){
        List<UserResponse> userResponses = userService.findAll();
        model.addAttribute("users", userResponses);
        return "user";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(Model model){
        if(message != null){
            model.addAttribute("message", message);
        }
        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }
        message = null;
        errorMessage = null;
        return "ui_updatePassword";
    }

    @PostMapping("/login")
    public String authenticate (@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model){
        String accessToken =  userService.authenticate(username, password);
        if(accessToken.equals("")){
            errorMessage = "Xác thực không thành công";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        String check =  userService.checkPassword();
        if(check.equals("")){
            model.addAttribute("accessToken", accessToken);
            return "redirect:/updatePassword";
        }

        String name = jwtFilter.getAuthenticaResponse().getName();
        model.addAttribute("name", name);
        model.addAttribute("accessToken", accessToken);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/home";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "ui_forgotPass";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model){

        message = userService.forgotPassword(email);

        if(message.equals("")){
            message = null;
            errorMessage = "Gửi email không thành công";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("email") String email,
                                Model model){

        if(message != null){
            model.addAttribute("message", message);
        }
        if(errorMessage != null){
            model.addAttribute("errorMessage", errorMessage);
        }
        message = null;
        errorMessage = null;
        model.addAttribute("token", token);
        model.addAttribute("email", email);
        return "ui_resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword( @RequestParam("email") String email,
                                        @RequestParam("token") String token,
                                        @RequestParam("password") String password,
                                        @RequestParam("repeatpassword") String repeatpassword,
                                        Model model){
//        if (password == null|| repeatpassword == null ||
//                !inputService.isValidInput(password) || !inputService.isValidInput(repeatpassword) ||
//                password.length() < 8){
//            errorMessage = "Không được để trống, mật khẩu dài hơn 8 ký tự, chứa chữ thường, chữ hoa, số, ký tự, @, !, () !";
//            return "redirect:/login";
//        }
        if(password.equals(repeatpassword)){
            System.out.println("Chuan bi gui");
            System.out.println(email);
            System.out.println(password);
            message = userService.resetPassword(token, email, password);
            if(message.equals("")){
                message = null;
                errorMessage = "Thay đổi mật khẩu không thành công";
                model.addAttribute("errorMessage", errorMessage);
            }
        }
        else {

            errorMessage = "Mật khẩu không khớp";
        }
        return "redirect:/home";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("newPassword") String password, Model model){

        if( !inputService.isValidPassword(password)){
            errorMessage = "Mật khẩu yêu cầu: chữ số, chữ hoa, chữ thường và ký tự đặc biệt.\nVí dụ: Abc123@def";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/updatePassword";
        }

        message = userService.updatepasword(password);
        if(message.equals("")){
            message = null;
            errorMessage = "Thay đổi mật khẩu không thành công";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "redirect:/home";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("newPassword") String password,
                                 @RequestParam("repeatNewPassword") String repeatPasword,
                                 Model model){
        if (!password.equals(repeatPasword)){
            model.addAttribute("message", "Mật khẩu không khớp.");
            model.addAttribute("messageType", "error");
            return "redirect:/profile";
        }
        if( !inputService.isValidPassword(password)){
            String mes = "Mật khẩu yêu cầu: chữ số, chữ hoa, chữ thường và ký tự đặc biệt.\nVí dụ: Abc123@def";
            model.addAttribute("message", mes);
            model.addAttribute("messageType", "error");
            return "redirect:/profile";
        }

        message = userService.updatepasword(password);
        if(message.equals("")){
            message = null;
            model.addAttribute("message", "Đổi mật khẩu thất bại.");
            model.addAttribute("messageType", "error");
        }
        else {
            model.addAttribute("message", "Đổi mật khẩu thành công.");
            model.addAttribute("messageType", "success");
        }
        return "redirect:/profile";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute UserRequestUI userRequestUI, Model model){

        String result =  userService.createUser(userRequestUI);
        System.out.println("result: " + result);
        if (result == null){
            errorMessage = "Đăng ký thất bại";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/home";

        }
        return "redirect:/home";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        if (jwtFilter.getAccessToken() != null){
            String name = jwtFilter.getAuthenticaResponse().getName();
            String email = jwtFilter.getAuthenticaResponse().getUserResponse().getEmail();
            String phoneNumber = jwtFilter.getAuthenticaResponse().getUserResponse().getPhoneNumber();
            String address = jwtFilter.getAuthenticaResponse().getUserResponse().getAddress();
            String gender = jwtFilter.getAuthenticaResponse().getUserResponse().getGender();
            Date dobirth = jwtFilter.getAuthenticaResponse().getDob();
            String role = jwtFilter.getAuthenticaResponse().getRole().getRoles();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dob = formatter.format(dobirth);
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("address", address);
            model.addAttribute("gender", gender);
            model.addAttribute("dob", dob);
            model.addAttribute("role", role);
            return "ui_profile";
        }
        return "redirect:/home";
    }
}
