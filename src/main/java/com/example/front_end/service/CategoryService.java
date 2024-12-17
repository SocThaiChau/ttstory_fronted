package com.example.front_end.service;

import com.example.front_end.model.dto.category.CategoryDTO;
import com.example.front_end.model.response.CategoryResponse;
import com.example.front_end.model.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.front_end.model.response.PagedResponse;

@Service
public class CategoryService {
    @Autowired
    private RestTemplate restTemplate;
    private String apiCategory = "http://localhost:8080/category/getAll";

    private String categorybyId = "http://localhost:8080/admin/category/";
    private String apiAddCategory = "http://localhost:8080/category/addCategory";


    //     * @param page The page number to fetch (starts from 0).
//            * @param size The number of items per page.
//     * @return PagedResponse containing the categories and pagination info.
//     */
    public PagedResponse<CategoryDTO> findAllWithPagination(int page, int size) {
        try {
            // Build the URL with query parameters
            String url = UriComponentsBuilder.fromHttpUrl(apiCategory)
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .toUriString();

            // Make the API call
            ResponseEntity<PagedResponse<CategoryDTO>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // Return the response body
            return responseEntity.getBody();
        } catch (Exception ex) {
            System.err.println("Error fetching paginated categories: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
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
    // Phương thức thêm danh mục
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        try {
            // Create an HttpHeaders object and set the necessary headers (e.g., Content-Type)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create an HttpEntity with the CategoryDTO as the body and headers
            HttpEntity<CategoryDTO> requestEntity = new HttpEntity<>(categoryDTO, headers);

            // Send the POST request
            ResponseEntity<CategoryDTO> response = restTemplate.exchange(
                    apiAddCategory,
                    HttpMethod.POST,
                    requestEntity,
                    CategoryDTO.class
            );

            // Return the created CategoryDTO from the response body
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null if there's an exception
        }
    }

}
