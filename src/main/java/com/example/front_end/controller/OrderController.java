package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.OrderRequestUI;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.request.AddToCartRequest;
import com.example.front_end.model.request.CartItemRequest;
import com.example.front_end.model.request.OrderRequest;
import com.example.front_end.model.response.*;
import com.example.front_end.service.AddressService;
import com.example.front_end.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {
    public static String errorMassage;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @GetMapping
    public String order(Model model){
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

            // Lấy danh sách địa chỉ
            List<AddressResponse> addressResponses = addressService.listAddress();
            model.addAttribute("addressResponses", addressResponses);
            System.out.println("addressResponses: " + addressResponses);

            // Lấy địa chỉ mặc định
            for (AddressResponse address : addressResponses){
                if("true".equalsIgnoreCase(String.valueOf(address.getIsDefault()))){
                    model.addAttribute("defaultAddress", address);
                    System.out.println("defaultAddress: " + address);
                }
            }

            OrderResponse orderResponse = userService.orderDetail();
            model.addAttribute("orderResponse", orderResponse);
            List<OrderItemResponse> orderItemResponses = orderResponse.getOrderItemResponses();
            List<ProductResponse> productDetails = new ArrayList<>();

            for (OrderItemResponse orderItem : orderItemResponses) {
                ProductResponse productDetail = userService.findProductById(orderItem.getProductId());
                productDetails.add(productDetail);
            }

            model.addAttribute("productDetails", productDetails);
            model.addAttribute("orderItemResponses", orderItemResponses);
            return "order";
        }
        return "redirect:/home";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@ModelAttribute OrderRequestUI orderRequestUI, RedirectAttributes redirectAttributes){
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }
        String result = userService.confirmAddOrder(orderRequestUI);
        if(result == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Đặt đơn hàng thất bại");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Đặt đơn hàng thành công");
        }

        return "redirect:/profile/order";
    }

    @PostMapping("/submit")
    public String submitOrder(@RequestBody List<AddToCartRequestUI> addToCartRequestUIS, RedirectAttributes redirectAttributes) {

        OrderRequest orderRequest = new OrderRequest();

        System.out.println("đã vào order");
        List<AddToCartRequest> cartItems = addToCartRequestUIS.stream()
                .map(item -> new AddToCartRequest(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        orderRequest.setCartItems(cartItems);

        for(AddToCartRequestUI item : addToCartRequestUIS){
            System.out.println("productId: " + item.getProductId() + " quantity: " + item.getQuantity());
        }
        String result = userService.addOrder(orderRequest);
        if(result == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Đặt đơn hàng thất bại");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Đặt đơn hàng thành công");
        }
        // Thêm các xử lý khác nếu cần thiết
        return "redirect:/order";
    }
    @PostMapping("/addOrder")
    public String addOrder(@RequestBody AddToCartRequestUI addToCartRequestUI, RedirectAttributes redirectAttributes) {

        OrderRequest orderRequest = new OrderRequest();

        System.out.println("đã vào order");
        System.out.println("productId: " + addToCartRequestUI.getProductId() + " quantity: " + addToCartRequestUI.getQuantity());

        List<AddToCartRequest> cartItems = new ArrayList<>();

        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setQuantity(addToCartRequestUI.getQuantity());
        addToCartRequest.setProductId(addToCartRequestUI.getProductId());

        cartItems.add(addToCartRequest);

        orderRequest.setCartItems(cartItems);

        String result = userService.addOrder(orderRequest);
        if(result == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Đặt đơn hàng thất bại");
        }
        else {
            redirectAttributes.addFlashAttribute("message", "Đặt đơn hàng thành công");
        }
        // Thêm các xử lý khác nếu cần thiết
        return "redirect:/order";
    }

}
