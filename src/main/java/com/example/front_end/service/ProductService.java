package com.example.front_end.service;

import com.cloudinary.Cloudinary;
import com.example.front_end.config.JwtFilter;
import com.example.front_end.exception.UserException;
import com.example.front_end.model.UI.AddToCartRequestUI;
import com.example.front_end.model.UI.ProductRequestUI;
import com.example.front_end.model.request.AddToCartRequest;
import com.example.front_end.model.request.ProductRequest;
import com.example.front_end.model.response.ProducListResponse;
import com.example.front_end.model.response.ProductResponse;
import com.example.front_end.model.response.ResponseObject;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtFilter jwtFilter;

    private final Cloudinary cloudinary;


    public ProductService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    private String allProduct = "http://localhost:8080/api/vp/getAllProduct";
    private String getProductByDate = "http://localhost:8080/api/vp/getProductByDate";
    private String getProductBySold = "http://localhost:8080/api/vp/getProductBySold";
    private String productFavorite = "http://localhost:8080/users/favorite";
    private String productDetail = "http://localhost:8080/api/vp/product/detail/";
    private String addProduct = "http://localhost:8080//api/vp/product/create";
    private String getAllMyProducts = "http://localhost:8080/api/vp/getAllMyProducts";
    private String favoriteApiUrl = "http://localhost:8080/users/favorite";

    public List<ProductResponse> findAllProduct() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(allProduct);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ProducListResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            ProducListResponse  productListResponse  = responseEntity.getBody();
            if (productListResponse  != null) {
                return productListResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ProductResponse> getAllMyProducts() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getAllMyProducts);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<ProducListResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            ProducListResponse  productListResponse  = responseEntity.getBody();
            if (productListResponse  != null) {
                return productListResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ProductResponse> get8ProductByDate() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getProductByDate);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ProducListResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            ProducListResponse  productListResponse  = responseEntity.getBody();
            if (productListResponse  != null) {
                return productListResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ProductResponse> get8ProductBySold() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getProductBySold);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ProducListResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            ProducListResponse  productListResponse  = responseEntity.getBody();
            if (productListResponse  != null) {
                return productListResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<ProductResponse> productFavorite() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(productFavorite);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<ProducListResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            ProducListResponse  productListResponse  = responseEntity.getBody();
            if (productListResponse  != null) {
                return productListResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public ProductResponse findProductById(Long id) {
        try {
            Map<String, String> params = new HashMap<>();
            String api = productDetail + id;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<ProductResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            ProductResponse productResponse  = responseEntity.getBody();
            if (productResponse  != null) {
                return productResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public String addProduct(ProductRequestUI productRequestUI, MultipartFile file) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(addProduct);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);

            ProductRequest productRequest = new ProductRequest();

            if (productRequestUI.getUrl() != null && !productRequestUI.getUrl().isEmpty()) {
                try {
                    Map<String, Object> uploadResult = upload(file);
                    String avatarUrl = (String) uploadResult.get("url");
                    productRequest.setUrl(avatarUrl);
                } catch (RuntimeException e) {
                    throw new UserException("Image upload failed: " + e.getMessage());
                }
            }

            productRequest.setName(productRequestUI.getName());
            productRequest.setCategoryId(productRequestUI.getCategoryId());
            productRequest.setQuantity(productRequestUI.getQuantity());
            productRequest.setPrice(productRequestUI.getPrice());
            productRequest.setDescription(productRequestUI.getDescription());
            productRequest.setPromotionalPrice(productRequestUI.getPromotionalPrice());


            HttpEntity<?> entity = new HttpEntity<>(productRequest,headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String result = responseEntity.getBody();
            if (result != null) {
                return result;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public Map<String, Object> upload(MultipartFile file) {
        try {
            return this.cloudinary.uploader().upload(file.getBytes(), Map.of());
        } catch (IOException io) {
            throw new RuntimeException("Image upload failed", io);
        }
    }
    public List<ProductResponse> searchProducts(String keyword) {
        try {
            String searchApi = "http://localhost:8080/api/vp/product/search"; // URL API tìm kiếm
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(searchApi)
                    .queryParam("keyword", keyword);

            ResponseEntity<ProducListResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Không cần headers trong trường hợp này
                    new ParameterizedTypeReference<>() {}
            );

            ProducListResponse productListResponse = responseEntity.getBody();
            if (productListResponse != null) {
                return productListResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean addProductToFavorites(Long userId, Long productId) {
        try {
            // Tạo URL để thêm sản phẩm vào danh sách yêu thích
            String url = String.format("%s/%d", favoriteApiUrl, productId);

            // Tạo headers và thêm token vào
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getAccessToken()); // Lấy access token

            // Gửi yêu cầu POST tới API
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<ResponseObject> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResponseObject.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getAccessToken() {
        // Lấy token từ JwtFilter hoặc từ nguồn lưu trữ token (localStorage, cookie...)
        return jwtFilter.getAccessToken();
    }
}
