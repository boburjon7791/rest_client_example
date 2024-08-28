package com.rest_client.rest_client_example.network.interceptors;

import com.rest_client.rest_client_example.network.RestNetwork;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


public class HeaderInterceptor implements ClientHttpRequestInterceptor{

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        HttpHeaders headers = request.getHeaders();

        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        if(request.getURI().toString().contains(RestNetwork.CAT_IMAGE_UPLOAD)) {
            headers.set(RestNetwork.X_API_KEY, RestNetwork.CAT_API_KEY);
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
        }

        return execution.execute(request, body);
    }
}
