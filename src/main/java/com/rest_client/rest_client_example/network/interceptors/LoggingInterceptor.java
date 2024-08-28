package com.rest_client.rest_client_example.network.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);

        int statusCode = response.getStatusCode().value();

        switch (statusCode) {
            case 200, 201, 204 ->{
                log.info("Called : request uri = {},  response status code = {}", request.getURI() + " " + request.getMethod(), statusCode);
            }
            case 400->{
                throw new RuntimeException("Bad request was send :  request uri = %s,  response status code = %s, response body = %s".formatted(request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes())));
            }
            case 401->{
                throw new RuntimeException("Unauthorized :  request uri = %s,  response status code = %s, response body = %s".formatted(request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes())));
            }
            case 403->{
                throw new RuntimeException("Access denied :  request uri = %s,  response status code = %s, response body = %s".formatted(request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes())));
            }
            case 404->{
                throw new RuntimeException("Not found :  request uri = %s,  response status code = %s, response body = %s".formatted(request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes())));
            }
            default->{
                throw new RuntimeException("Error was occurred :  request uri = %s,  response status code = %s, response body = %s".formatted(request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes())));
            }
        }
        return response;
    }
}
