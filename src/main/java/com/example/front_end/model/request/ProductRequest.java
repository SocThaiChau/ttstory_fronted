package com.example.front_end.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Double promotionalPrice;

    private Integer quantity;

    private Integer quantityAvailable;

    private Integer numberOfRating;

    private Integer favoriteCount;

    private Integer sold;

    private Boolean isActive;

    private Boolean isSelling;

    private Float rating;

    private String createdBy;

    private String lastModifiedBy;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModifiedDate;

    private Long categoryId;
    private String url;

}
