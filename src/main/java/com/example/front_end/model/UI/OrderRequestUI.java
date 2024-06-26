package com.example.front_end.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestUI {
    private Long id;
    private Long idAddress;
    private String note;
    private Boolean isPaidBefore;
    private String status;
    private String paymentType;
    private Long productId;
    private Integer quantity;
    private List<AddToCartRequestUI> cartItems;
}
