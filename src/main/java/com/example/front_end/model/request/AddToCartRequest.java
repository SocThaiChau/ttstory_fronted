package com.example.front_end.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequest {
    private Integer productId;
    private Integer quantity;
}
