package com.example.front_end.service;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.dto.order.OrderRequest;
import com.example.front_end.model.dto.order.OrderParentResponse;
import com.example.front_end.model.dto.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtFilter jwtFilter;

    private String createOrder = "http://localhost:8080/api/orders/createOrder";

    private String myOrder = "http://localhost:8080/api/orders/user/";
    public OrderParentResponse createOrder(OrderRequest orderRequest) {
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(createOrder);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            String accessToken = jwtFilter.getAccessToken();
            HttpHeaders headers = new HttpHeaders();
            headers.set("AUTHORIZATION", accessToken);


            HttpEntity<?> entity = new HttpEntity<>(orderRequest,headers);
            ResponseEntity<OrderParentResponse> responseEntity = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity, // Include the HttpEntity with headers,
                    new ParameterizedTypeReference<>() {}
            );

            OrderParentResponse result = responseEntity.getBody();
            if(result != null){
                return result;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public List<OrderResponse> orderResponses(Long userId){
        try {
            Map<String, String> params = new HashMap<>();

            String api = myOrder + userId + "/all-orders";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api);
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


}
