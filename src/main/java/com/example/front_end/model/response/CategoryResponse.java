package com.example.front_end.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private String imageUrl;
    private List<CategoryResponse> data;
    private String image;
}
