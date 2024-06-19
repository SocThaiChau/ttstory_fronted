package com.example.front_end.model.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartRequestUI {
    private Integer productId;
    private Integer quantity;
}
