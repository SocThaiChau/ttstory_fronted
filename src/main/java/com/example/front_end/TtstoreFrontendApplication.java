package com.example.front_end;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TtstoreFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TtstoreFrontendApplication.class, args);
	}

	@Bean
	public Cloudinary getCloudinary(){
		Map config = new HashMap();
		config.put("cloud_name", "dwkmrgleh");
		config.put("api_key", "557369255376338");
		config.put("api_secret", "nIuE6-yeLORWDrQ_5RGN1DKzRGw");
		config.put("secure", true);
		return new Cloudinary(config);
	}
}
