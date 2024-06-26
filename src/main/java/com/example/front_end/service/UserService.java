package com.example.front_end.service;

import com.cloudinary.Cloudinary;
import com.example.front_end.config.JwtFilter;
import com.example.front_end.exception.UserException;
import com.example.front_end.model.UI.*;
import com.example.front_end.model.request.*;
import com.example.front_end.model.response.*;
import jdk.jshell.spi.ExecutionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    private String apiUrl = "http://localhost:8080/admin/user";

    private String loginUrl = "http://localhost:8080/api/v1/auth/login";

    private String forgotPasswordUrl = "http://localhost:8080/send-mail";
    private String resetPasswordUrl = "http://localhost:8080/reset-password";

    private String checkPassword = "http://localhost:8080/users/checkPassword";
    private String updatePassword = "http://localhost:8080/users/updatePassword";
    private String createUser = "http://localhost:8080/admin/users/create";
    private String allProduct = "http://localhost:8080/users/getAllProduct";
    private String getProductByDate = "http://localhost:8080/users/getProductByDate";
    private String getProductBySold = "http://localhost:8080/users/getProductBySold";

    private String productFavorite = "http://localhost:8080/users/favorite";

    private String productDetail = "http://localhost:8080/users/product/detail/";

    private String updateUser = "http://localhost:8080/users/profile";

    private String findUserById = "http://localhost:8080/users/getUser/";
    private String cartDetail = "http://localhost:8080/users/cart/detail";

    private String addToCart = "http://localhost:8080/users/cart/add";
    private String deleteItem = "http://localhost:8080/users/delete/cartItem/";

    private String myOrder = "http://localhost:8080/order/my-orders";

    private String orderPending = "http://localhost:8080/order/pending";

    private String addAddress = "http://localhost:8080/address/add";
    private String deleteAddress = "http://localhost:8080/address/delete/";
    private String updateAddress = "http://localhost:8080/address/update/";
    private String confirmOrder = "http://localhost:8080/order/confirmOrder/";

    private String addOrder = "http://localhost:8080/order/addOrder";

    private String addProduct = "http://localhost:8080/admin/product/create";

    private String categorybyId = "http://localhost:8080/admin/category/";






    @Autowired
    private JwtFilter jwtFilter;

    private final Cloudinary cloudinary;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public List<UserResponse> findAll() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
//            String accessToken = jwtFilter.getAccessToken();
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token
//            // Create an HttpEntity with the headers
//            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<List<UserResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<UserResponse> userResponses = responseEntity.getBody();
            if (userResponses != null) {
                return userResponses;
            }

            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public String authenticate(String username, String password) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(loginUrl);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            AuthenticationRequest authenticationRequest = new AuthenticationRequest();
            authenticationRequest.setEmail(username);
            authenticationRequest.setPassword(password);


            HttpEntity<?> entity = new HttpEntity<>(authenticationRequest);
            ResponseEntity<AuthenticaResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            AuthenticaResponse authenticaResponse = responseEntity.getBody();
            if (authenticaResponse != null) {
                String token = "Bearer " + authenticaResponse.getToken();
                jwtFilter.setAccessToken(token);
                jwtFilter.setAuthenticaResponse(authenticaResponse);
                return "Xac thuc thanh cong";
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }

    public void logout(){
        jwtFilter.setAccessToken(null);
    }

    public String forgotPassword(String email) {
        try {
            Map<String, String> params = new HashMap<>();

            System.out.println("email: " + email);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(forgotPasswordUrl);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            HttpEntity<?> entity = new HttpEntity<>(email);
            ResponseEntity<SuccessMessage> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            SuccessMessage successMessage = responseEntity.getBody();
            return successMessage.getMessage();
        } catch (Exception ex) {
            return "";
        }
    }

    public String resetPassword(String token, String email, String newPassword) {
        try {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("token", token);
            queryParams.put("email", email);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(resetPasswordUrl);
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(newPassword, headers);
            ResponseEntity<SuccessMessage> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );
            SuccessMessage successMessage = responseEntity.getBody();
            return successMessage.getMessage();
        } catch (Exception ex) {
            return "";
        }
    }

    public String checkPassword() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(checkPassword);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String successMessage = responseEntity.getBody();
            if (successMessage != null){
                return successMessage;
            }
            return "";
        } catch (Exception ex) {
            return "";
        }
    }

    public String updatepasword(String password) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(updatePassword);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token

            UserRequest userRequest = new UserRequest();
            userRequest.setPassword(password);

            HttpEntity<?> entity = new HttpEntity<>(userRequest,headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String userResponse = responseEntity.getBody();
            if (userResponse != null) {
                return userResponse;
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }

    public String createUser(UserRequestUI userRequestUI) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createUser);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            String bd = userRequestUI.getDob();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dob = dateFormat.parse(bd);

            UserRequest userRequest = new UserRequest();
            userRequest.setEmail(userRequestUI.getEmail());
            userRequest.setName(userRequestUI.getName());
            userRequest.setGender(userRequestUI.getGender());
            userRequest.setDob(dob);
            userRequest.setPhoneNumber(userRequestUI.getPhoneNumber());
            userRequest.setAddress(userRequestUI.getAddress());

            HttpEntity<?> entity = new HttpEntity<>(userRequest);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String userResponse = responseEntity.getBody();
            if (userResponse != null) {
                return userResponse;
            }
            return null;

        } catch (Exception ex) {
            return null;
        }
    }

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

    public String updateUser(UserRequest userRequestUI, Integer id, MultipartFile avatarUrlFile) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(updateUser);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }


            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            UserRequest userRequest = new UserRequest();

            if (userRequestUI.getAvatarUrl() != null && !userRequestUI.getAvatarUrl().isEmpty()) {
                try {
                    Map<String, Object> uploadResult = upload(avatarUrlFile);
                    String avatarUrl = (String) uploadResult.get("url");
                    userRequest.setAvatarUrl(avatarUrl);
                } catch (RuntimeException e) {
                    throw new UserException("Image upload failed: " + e.getMessage());
                }
            }
            userRequest.setName(userRequestUI.getName());
            userRequest.setGender(userRequestUI.getGender());
            userRequest.setDob(userRequestUI.getDob());
            userRequest.setPhoneNumber(userRequestUI.getPhoneNumber());
            userRequest.setAddress(userRequestUI.getAddress());

            HttpEntity<?> entity = new HttpEntity<>(userRequest, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String userResponse = responseEntity.getBody();
            if (userResponse != null) {
                return userResponse;
            }
        } catch (Exception ex) {
            return "";
        }
        return "";
    }

    public Map<String, Object> upload(MultipartFile file) {
        try {
            return this.cloudinary.uploader().upload(file.getBytes(), Map.of());
        } catch (IOException io) {
            throw new RuntimeException("Image upload failed", io);
        }
    }

    public UserResponse findUserById(Long id) {
        try {
            Map<String, String> params = new HashMap<>();
//            String api = productDetail + id;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(findUserById + id);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<UserResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            UserResponse userResponse  = responseEntity.getBody();

            if (userResponse  != null) {
                return userResponse;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public CartResponse cartDetail() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(cartDetail);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<CartResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            CartResponse cartResponse = responseEntity.getBody();
            if (cartResponse != null) {
                return cartResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public String addCart(AddToCartRequestUI addToCartRequestUI) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(addToCart);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            AddToCartRequest addToCartRequest = new AddToCartRequest();
            addToCartRequest.setQuantity(addToCartRequestUI.getQuantity());
            addToCartRequest.setProductId(addToCartRequestUI.getProductId());

            HttpEntity<?> entity = new HttpEntity<>(addToCartRequest,headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String cartResponse = responseEntity.getBody();
            if (cartResponse != null) {
                return cartResponse;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public String deleteCartItem(Long id) {
        try {
            Map<String, String> params = new HashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(deleteItem + id);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.DELETE,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String response = responseEntity.getBody();
            if (response != null) {
                return response;
            }
            return null;

        } catch (Exception ex) {
            return null;
        }
    }

    public String updateCartItem(int id, int quantity) {
        try {
            Map<String, String> params = new HashMap<>();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(addToCart);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            System.out.println("id: " + id + "quantity: " + quantity);
            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);

            AddToCartRequest addToCartRequest = new AddToCartRequest();
            addToCartRequest.setProductId(id);
            addToCartRequest.setQuantity(quantity);

            HttpEntity<?> entity = new HttpEntity<>(addToCartRequest,headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String response = responseEntity.getStatusCode().toString();
            if (response != null) {
                return response;
            }
            return null;

        } catch (Exception ex) {
            return null;
        }
    }

    public List<OrderResponse> orderResponses(){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(myOrder);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<List<OrderResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<OrderResponse>  orderResponse  = responseEntity.getBody();
            if (orderResponse  != null) {
                return orderResponse;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<OrderResponse> allOrderResponses(){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/order/getAll");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }



            ResponseEntity<List<OrderResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<OrderResponse>  orderResponse  = responseEntity.getBody();
            if (orderResponse  != null) {
                return orderResponse;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
    public OrderResponse orderDetail() {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(orderPending);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<OrderResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            OrderResponse orderResponse = responseEntity.getBody();
            if (orderResponse != null) {
                return orderResponse;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<CategoryResponse> listCategory(){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/users/categories");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<CategoryResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            CategoryResponse categoryResponse  = responseEntity.getBody();
            if (categoryResponse  != null) {
                return categoryResponse.getData();
            }
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    public CategoryResponse categoryById(Long id) {
        try {
            Map<String, String> params = new HashMap<>();
//            String api = productDetail + id;

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(categorybyId + id);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            ResponseEntity<CategoryResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            CategoryResponse categoryResponse  = responseEntity.getBody();

            if (categoryResponse  != null) {
                return categoryResponse;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
    public List<AddressResponse> listAddress(){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/address/my-addresses");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken); // Replace with your actual token


            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<List<AddressResponse>> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            List<AddressResponse> addressResponses  = responseEntity.getBody();
            if (addressResponses  != null) {
                return addressResponses;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }

    }

    public String addAddress(AddressRequestUI addressRequestUI) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(addAddress);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setFullName(addressRequestUI.getFullName());
            addressRequest.setDistrict(addressRequestUI.getDistrict());
            addressRequest.setWard(addressRequestUI.getWard());
            addressRequest.setCity(addressRequestUI.getCity());
            addressRequest.setPhoneNumber(addressRequestUI.getPhoneNumber());

            HttpEntity<?> entity = new HttpEntity<>(addressRequest,headers);
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
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }
    public String deleteAddress(Long id) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(deleteAddress + id);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.DELETE,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String result = responseEntity.getBody();
            if (result != null) {
                return result;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public String updateAddress(AddressRequestUI addressRequestUI) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(updateAddress + addressRequestUI.getId());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setFullName(addressRequestUI.getFullName());
            addressRequest.setDistrict(addressRequestUI.getDistrict());
            addressRequest.setWard(addressRequestUI.getWard());
            addressRequest.setCity(addressRequestUI.getCity());
            addressRequest.setPhoneNumber(addressRequestUI.getPhoneNumber());

            HttpEntity<?> entity = new HttpEntity<>(addressRequest,headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            String result = responseEntity.getBody();
            if (result != null) {
                return result;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public String confirmAddOrder(OrderRequestUI orderRequestUI) {
        try {
            Map<String, String> params = new HashMap<>();

            System.out.println(confirmOrder + orderRequestUI.getId());
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(confirmOrder + orderRequestUI.getId());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setIdAddress(orderRequestUI.getIdAddress());
            orderRequest.setPaymentType(orderRequestUI.getPaymentType());

            HttpEntity<?> entity = new HttpEntity<>(orderRequest,headers);
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
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public String addOrder(OrderRequest orderRequest) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(addOrder);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);

            for (AddToCartRequest item : orderRequest.getCartItems()){
                System.out.println("ProductId: " + item.getProductId() + " Quantity: " + item.getQuantity());
            }

            HttpEntity<?> entity = new HttpEntity<>(orderRequest,headers);
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
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
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
}
