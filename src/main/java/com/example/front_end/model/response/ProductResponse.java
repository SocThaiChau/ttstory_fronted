package com.example.front_end.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse implements Serializable {

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
    private Double rating;
    private String createdBy;
    private String lastModifiedBy;
    private String createdDate;
    private String lastModifiedDate;
    private String url;
    private UserResponse userResponse;
    private CategoryResponse categoryResponse;
    private List<ImageResponse> images;
    private Long userId;
    private Long categoryId;
    private String categoryName;
    private ProductResponse data;

}
