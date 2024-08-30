package com.rest_client.rest_client_example.network;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
class SingleInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

            HttpHeaders headers = request.getHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            if (request.getURI().toString().contains(RestNetwork.CAT_IMAGE_UPLOAD)){
                headers.set(RestNetwork.X_API_KEY, RestNetwork.CAT_API_KEY);
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
            }

            ClientHttpResponse response = execution.execute(request, body);

            int statusCode = response.getStatusCode().value();

            switch (statusCode) {
                case 200, 201, 204 ->{
                    log.info("Called : request uri = {},  response status code = {}", request.getURI() + " " + request.getMethod(), statusCode);
                }
                case 400->{
                    log.error("Bad request was send :  request uri = {},  response status code = {}, response body = {}", request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes()));
                }
                case 401->{
                    log.error("Unauthorized :  request uri = {},  response status code = {}, response body = {}", request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes()));
                }
                case 403->{
                    log.error("Access denied :  request uri = {},  response status code = {}, response body = {}", request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes()));
                }
                case 404->{
                    log.error("Not found :  request uri = {},  response status code = {}, response body = {}", request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes()));
                }
                default->{
                    log.error("Other error :  request uri = {},  response status code = {}, response body = {}", request.getURI() + " " + request.getMethod(), statusCode, new String(response.getBody().readAllBytes()));
                }
            }
            return response;
        }
}
