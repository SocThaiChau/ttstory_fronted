package com.example.front_end.service;

import com.example.front_end.model.dto.category.CategoryDTO;
import com.example.front_end.model.response.CategoryResponse;
import com.example.front_end.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    @Autowired
    private RestTemplate restTemplate;

    private String apiCategory = "http://localhost:8080/category/getAll";

    private String categorybyId = "http://localhost:8080/admin/category/";

    public List<CategoryDTO> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiCategory);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<List<CategoryDTO>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<CategoryDTO> categoryDTOS = responseEntity.getBody();
            if (categoryDTOS != null) {
                return categoryDTOS;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public CategoryDTO categoryById(Long id) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(categorybyId + id);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            CategoryDTO categoryDTO  = responseEntity.getBody();

            if (categoryDTO  != null) {
                return categoryDTO;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

}
