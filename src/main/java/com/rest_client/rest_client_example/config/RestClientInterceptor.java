package com.rest_client.rest_client_example.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RestClientInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public @NotNull ClientHttpResponse intercept(@NonNull HttpRequest request, byte[] body,
                                                 @NonNull ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);
        HttpStatusCode statusCode = response.getStatusCode();
        if (statusCode.is2xxSuccessful()) {
            log.info("Called : request method = {},  request uri = {},  response status code = {}", request.getMethod(), request.getURI(), response.getStatusCode());
        }else if(!statusCode.is2xxSuccessful()) {
            throw new RuntimeException("Error was occurred : request method = %s,  request uri = %s,  response status code = %s".formatted(request.getMethod(), request.getURI(), response.getStatusCode()));
        }
        return response;
    }
}
