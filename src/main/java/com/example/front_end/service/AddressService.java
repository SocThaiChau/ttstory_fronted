package com.example.front_end.service;

import com.example.front_end.config.JwtFilter;
import com.example.front_end.model.UI.AddressRequestUI;
import com.example.front_end.model.request.AddressRequest;
import com.example.front_end.model.response.AddressResponse;
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
public class AddressService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtFilter jwtFilter;

    private String getAddress = "http://localhost:8080/address/my-addresses";
    private String addAddress = "http://localhost:8080/address/add";
    private String deleteAddress = "http://localhost:8080/address/delete/";
    private String updateAddress = "http://localhost:8080/address/update/";

    public List<AddressResponse> listAddress(){
        try {
            Map<String, String> params = new HashMap<>();

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getAddress);
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

}
