package com.example.front_end.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse implements Serializable {
    private Long id;
    private Integer quantity;
    private Long productId;  // Only product ID
    private Double subtotal;
    private Date createdDate;
    private Date lastModifiedDate;
}
