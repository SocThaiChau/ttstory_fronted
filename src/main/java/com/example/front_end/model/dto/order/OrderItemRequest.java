package com.example.front_end.model.dto.order;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class OrderItemRequest {

    private int quantity;

    private int productId;

    private double subtotal;

    public OrderItemRequest(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
