package com.example.front_end.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse implements Serializable {
    private Long id;
    private String status;
    private String note;
    private Double total;
    private Boolean isPaidBefore;
    private String paymentType;
    private Integer totalItem;
    private String createdBy;
    private String lastModifiedBy;
    private Date createdDate;
    private Date lastModifiedDate;
    private Long addressId;  // Only address ID
    private Long userId;  // Only user ID
    private Long productId;
    private List<OrderItemResponse> orderItemResponses;
}
