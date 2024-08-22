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
        try {
            ClientHttpResponse clientHttpResponse = execution.execute(request, body);
            HttpStatusCode statusCode = clientHttpResponse.getStatusCode();
            if (statusCode.is2xxSuccessful()) {
                successResponseAction(request, clientHttpResponse);
            }else if(!statusCode.is2xxSuccessful()) {
                errorResponseAction(request, clientHttpResponse);
            }
            return clientHttpResponse;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * this is for handling external API success responses
     * */
    private static void successResponseAction(HttpRequest request, ClientHttpResponse response){
        try {
            log.info("Called : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this is for handling third party API fail responses
     * */
    private static void errorResponseAction(HttpRequest request, ClientHttpResponse response){
        try {
            log.error("Error was occurred : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
