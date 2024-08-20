package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.client.RestNetwork;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RestClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestClientExampleApplication.class, args);
	}

}
