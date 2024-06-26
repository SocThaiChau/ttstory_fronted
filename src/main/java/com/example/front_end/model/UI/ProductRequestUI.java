package com.example.front_end.model.UI;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestUI {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Double promotionalPrice;

    private Integer quantity;

    private Long categoryId;

    private String url;

}
