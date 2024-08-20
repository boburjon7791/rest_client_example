package com.rest_client.rest_client_example.client;

import com.rest_client.rest_client_example.utils.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class RestNetwork {
    private static final RestClient restClient=RestClient.builder().build();

    public static <T> String get(String apiUrl, Map<String, ?> params){
        return restClient.get()
                .uri(apiUrl, params)
                .retrieve()
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .body(String.class);
    }

    public static <T> String post(String apiUrl, T body){
        return restClient.post()
                .uri(apiUrl)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .body(String.class);
    }

    public static <T> String post(String apiUrl, T body, HttpHeaders inputHeaders){
        return restClient.post()
                .uri(apiUrl)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> httpHeaders.addAll(inputHeaders))
                .retrieve()
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .body(String.class);
    }

    public static <T> String post(String apiUrl, T body, Map<String, ?> params){
        return restClient.post()
                .uri(apiUrl, params)
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .body(String.class);
    }

    public static String post(String apiUrl, MultiValueMap<String, String> formData){
        return restClient.post()
                .uri(apiUrl)
                .body(formData)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .body(String.class);
    }
}
