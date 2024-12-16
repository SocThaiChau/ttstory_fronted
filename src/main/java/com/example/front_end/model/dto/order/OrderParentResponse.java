package com.example.front_end.model.dto.order;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class OrderParentResponse {
    private Long id;

    private String status;

    private Double total;

    private Boolean isPaidBefore;

    private String paymentType;

    private Date createdDate;

    private String paymentUrl;
}
