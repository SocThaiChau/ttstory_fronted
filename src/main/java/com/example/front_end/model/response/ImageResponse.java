package com.example.front_end.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse implements Serializable {
    private Long id;
    private String url;
    private ProductResponse productResponse;
}
