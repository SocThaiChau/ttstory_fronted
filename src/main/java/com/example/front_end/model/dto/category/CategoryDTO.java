package com.example.front_end.model.dto.category;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class CategoryDTO {
    private String id;
    private String name;
    private MultipartFile imageFile;  // Tệp ảnh tải lên
    private String imageUrl;  // URL của ảnh nếu có
    private String image;  // Đường dẫn đến ảnh đã lưu hoặc URL
}
