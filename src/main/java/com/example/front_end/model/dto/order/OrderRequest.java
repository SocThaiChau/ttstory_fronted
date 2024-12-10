package com.example.front_end.model.dto.order;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class OrderRequest {

    private Long addressId;

    private String paymentType;

    private boolean isPaidBefore;

    private List<OrderItemRequest> items;

}
