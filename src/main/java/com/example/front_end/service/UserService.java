package com.example.front_end.service;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.UserRequestUI;
import com.example.front_end.model.request.AuthenticationRequest;
import com.example.front_end.model.request.UserRequest;
import com.example.front_end.model.response.AuthenticaResponse;
import com.example.front_end.model.response.SuccessMessage;
import com.example.front_end.model.response.UserResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Autowired
    private JwtFilter jwtFilter;


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
        } catch (Exception ex) {
            return null;
        }
        return null;
    }
}
