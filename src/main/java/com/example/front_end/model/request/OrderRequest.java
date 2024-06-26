package com.example.front_end.model.request;

import com.example.front_end.model.response.CartItemResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Long idAddress;
    private String note;
    private Boolean isPaidBefore;
    private String status;
    private String paymentType;
    private List<AddToCartRequest> cartItems;

}
