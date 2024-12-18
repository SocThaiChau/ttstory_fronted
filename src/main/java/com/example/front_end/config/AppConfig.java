package com.example.front_end.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        // Tạo RestTemplate mới với FormHttpMessageConverter hỗ trợ multipart/form-data
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpMessageConverter<?> converter = new FormHttpMessageConverter();
        restTemplate.getMessageConverters().add(converter); // Thêm FormHttpMessageConverter vào danh sách converters
        return restTemplate;
    }

}
