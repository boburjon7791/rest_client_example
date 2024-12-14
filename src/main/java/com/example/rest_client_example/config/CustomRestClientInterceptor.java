package com.example.rest_client_example.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Slf4j
public class CustomRestClientInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        /*
        * write custom http headers
        * */
        if (request.getURI().toString().equals(RestNetwork.JSON_PLACE_HOLDER)) {
            headers.add(RestNetwork.API_KEY_NAME, RestNetwork.API_KEY_VALUE);
        }

        Instant start = Instant.now();
        ClientHttpResponse response = execution.execute(request, body);
        Instant end = Instant.now();
        long responseTime = Duration.between(start, end).toMillis();

        HttpStatusCode statusCode = response.getStatusCode();
        switch (statusCode.value()) {
            case 200, 201, 204 -> writeSuccessLog(request, response, responseTime);
            case 400, 401, 403, 404, 500, 502 -> writeErrorLog(request, response, responseTime);
        }

        return response;
    }

    @SneakyThrows
    public void writeSuccessLog(HttpRequest request, ClientHttpResponse response, long responseTime){
        log.info("http request's response : {}, {}, {}, {} millisecond", request.getURI(), request.getMethod(), response.getStatusCode(), responseTime);
    }

    @SneakyThrows
    public void writeErrorLog(HttpRequest request, ClientHttpResponse response, long responseTime){
        log.error("http request's response : {}, {}, {}, {}, {} millisecond", request.getURI(), request.getMethod(), response.getStatusCode(), new String(response.getBody().readAllBytes()), responseTime);
    }
}
