package com.example.front_end.controller;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.OrderRequestUI;
import com.example.front_end.model.dto.order.OrderItemRequest;
import com.example.front_end.model.dto.order.OrderParentResponse;
import com.example.front_end.model.dto.order.OrderRequest;
import com.example.front_end.model.dto.user.UserDTO;
import com.example.front_end.model.response.AddressResponse;
import com.example.front_end.model.response.CartResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.service.AddressService;
import com.example.front_end.service.OrderService;
import com.example.front_end.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @GetMapping
    public String order(HttpSession session, Model model){
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

            // Lấy dữ liệu từ session
            OrderRequest orderRequest = (OrderRequest) session.getAttribute("orderRequest");

            List<ProductResponse> productDetails = new ArrayList<>();

            int totalItems = 0;
            double totalPrice = 0.0;

            if(orderRequest != null){
                for (OrderItemRequest orderItem : orderRequest.getItems()) {
                    ProductResponse productDetail = userService.findProductById((long) orderItem.getProductId());
                    productDetails.add(productDetail);

                    // Tính toán subtotal
                    double promotionalPrice = productDetail.getPromotionalPrice();
                    int quantity = orderItem.getQuantity();
                    orderItem.setSubtotal(promotionalPrice * quantity);
                    totalPrice += promotionalPrice * quantity; // Cộng tổng tiền
                    totalItems += quantity; // Cộng tổng số lượng
                }
            }

            model.addAttribute("productDetails", productDetails);
            model.addAttribute("orderItems", orderRequest.getItems());
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("totalItems", totalItems);

            return "order";
        }
        return "redirect:/home";
    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(@ModelAttribute OrderRequestUI orderRequestUI, HttpSession session, RedirectAttributes redirectAttributes){
        if (jwtFilter.getAccessToken() == null) {
            return "redirect:/home";
        }
        // Lấy dữ liệu từ session
        OrderRequest orderRequest = (OrderRequest) session.getAttribute("orderRequest");
        orderRequest.setAddressId(orderRequestUI.getIdAddress());
        orderRequest.setPaidBefore(false);
        orderRequest.setPaymentType(orderRequestUI.getPaymentType());

        OrderParentResponse result = orderService.createOrder(orderRequest);

        if(result == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Đặt đơn hàng thất bại");
        }
        else {
            if (orderRequest.getPaymentType().equals("VNPAY")){
                return "redirect:" + result.getPaymentUrl();
            }
            redirectAttributes.addFlashAttribute("message", "Đặt đơn hàng thành công");
        }

        return "redirect:/profile/order";
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> placeOrder(@RequestBody List<AddToCartRequestUI> addToCartRequestUIS, HttpSession session) {

        // Tạo đối tượng OrderRequest từ addToCartRequestUIS
        OrderRequest orderRequest = new OrderRequest();
        List<OrderItemRequest> orderItems = addToCartRequestUIS.stream()
                .map(item -> new OrderItemRequest(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());
        orderRequest.setItems(orderItems);

        // Lưu OrderRequest vào session
        session.setAttribute("orderRequest", orderRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order placed successfully!");

        return ResponseEntity.ok(response);
    }

}
