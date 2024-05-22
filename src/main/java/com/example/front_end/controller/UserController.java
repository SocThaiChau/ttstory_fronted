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

    @GetMapping("/login")
    public String login(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "ui_login";
        }
        return "redirect:/home";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "ui_login";
        }
        return "ui_updatePassword";
    }

    @PostMapping("/login")
    public String authenticate (@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model){
        if (jwtFilter.getAccessToken() != null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "redirect:/";
        }
        String accessToken =  userService.authenticate(username, password);
        if(accessToken.equals("")){
            errorMessage = "Xác thực không thành công";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/login";
        }

        String check =  userService.checkPassword();
        if(check.equals("")){
            model.addAttribute("accessToken", accessToken);
            return "redirect:/updatePassword";
        }

        model.addAttribute("accessToken", accessToken);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/login";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model){
        if (jwtFilter.getAccessToken() == null){
            if(message != null){
                model.addAttribute("message", message);
            }
            if(errorMessage != null){
                model.addAttribute("errorMessage", errorMessage);
            }
            message = null;
            errorMessage = null;
            return "ui_forgotPass";
        }
        message = null;
        errorMessage = null;
        return "redirect:/home";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, Model model){
        System.out.println("email: " + email);
        message = userService.forgotPassword(email);
        System.out.println("message: " + message);
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


        if (jwtFilter.getAccessToken() == null){
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
        return "redirect:/home";
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
        return "redirect:/login";
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
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String register(){
        return "ui_register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute UserRequestUI userRequestUI, Model model){

        System.out.println("userRequestUI: " + userRequestUI);

        System.out.println("dob: " + userRequestUI.getDob());
        String result =  userService.createUser(userRequestUI);
        System.out.println("result: " + result);
        if (result == null){
            errorMessage = "Đăng ký thất bại";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/register";
        }
        return "redirect:/login";
    }
}
