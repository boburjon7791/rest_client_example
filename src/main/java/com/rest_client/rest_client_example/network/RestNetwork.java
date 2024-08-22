package com.rest_client.rest_client_example.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class RestNetwork {
    private static final RestClient restClient=RestClient.builder().build();
    private static final ObjectMapper objectMapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String get(String apiUrl, Map<String, String> params, HttpHeaders headers){
        return restClient.get()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .body(String.class);
    }

    public static InputStream getMultipart(String apiUrl, Map<String, String> params, HttpHeaders headers){
        byte[] body = restClient.get()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .body(byte[].class);
        return new ByteArrayInputStream(Objects.requireNonNull(body));
    }

    /*
    * POST requests
    * */
    public static <T> String post(String apiUrl, T body, Map<String, String> params, HttpHeaders headers, MediaType mediaType){
        return restClient.post()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .body(body)
                .contentType(mediaType)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .body(String.class);
    }

    public static String post(String apiUrl, MultiValueMap<String, String> formData, HttpHeaders headers){
        return restClient.post()
                .uri(apiUrl)
                .body(formData)
                .headers(httpHeaders->httpHeaders.addAll(headers))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .body(String.class);
    }

    /*
    * PUT request
    * */
    public static <T> String put(String apiUrl, T body, Map<String, String> params, HttpHeaders headers, MediaType mediaType){
        return restClient.put()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .body(body)
                .contentType(mediaType)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .body(String.class);
    }

    public static String put(String apiUrl, MultiValueMap<String, String> formData, HttpHeaders headers){
        return restClient.post()
                .uri(apiUrl)
                .body(formData)
                .headers(httpHeaders->httpHeaders.addAll(headers))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .body(String.class);
    }

    /*
    * DELETE request
    * */
    public static void delete(String apiUrl, Map<String, String> params, HttpHeaders headers){
         restClient.delete()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .onStatus(RestNetwork::isFail,    RestNetwork::errorResponseAction)
                .onStatus(RestNetwork::isSuccess, RestNetwork::successResponseAction)
                .toBodilessEntity();
    }

    public static <T> List<T> parseFromJsonArray(String jsonBody, Class<T> type){
        try {
            return objectMapper.readValue(jsonBody, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseFromJson(String jsonBody, Class<T> type){
        try {
            return objectMapper.readValue(jsonBody, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> emptyParam(){
        return new HashMap<>();
    }

    public static HttpHeaders emptyHeaders(){
        return new HttpHeaders();
    }

    public static MultiValueMap<String, String> convertToMultiValueMap(Map<String, String> params){
        Map<String, List<String>> listMap = params.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, object -> List.of(object.getValue())));
        return new LinkedMultiValueMap<>(listMap);
    }

    /**
     * External APIs
     * */
    public static final String POSTS_API="https://jsonplaceholder.typicode.com/posts";
    public static final String IMAGE_URL="https://media.wired.com/photos/64daad6b4a854832b16fd3bc/master/w_1920,c_limit/How-to-Choose-a-Laptop-August-2023-Gear.jpg";
    public static final String IMAGE_URL2="https://i.pinimg.com/originals/72/22/78/722278c489e2563abc3e0aa91901bb16.jpg";
    public static final String IMAGE_UPLOAD="https://v2.convertapi.com/upload";
    public static final String HEADER_TEST_URL="http://localhost:8080/test-headers";
    public static final String FILE_URL_WITH_HEADERS="http://localhost:8080/test-multipart-headers";
    public static final String FORM_DATA_TEST="http://localhost:8080/test-multi-value-map";

    /**
     * this is for handling external API success responses
     * */
    private static void successResponseAction(HttpRequest request, ClientHttpResponse response){
        try {
            log.info("Called : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this is for checking external API success response
     * */
    private static boolean isSuccess(HttpStatusCode httpStatusCode){
        return httpStatusCode.is2xxSuccessful();
    }

    /**
     * this is for handling third party API fail responses
     * */
    private static void errorResponseAction(HttpRequest request, ClientHttpResponse response){
        try {
            log.error("Error was occurred : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            throw new RuntimeException(String.valueOf(response.getStatusCode().value()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this is for checking external API fail response
     * */
    private static boolean isFail(HttpStatusCode httpStatusCode){
        return !httpStatusCode.is2xxSuccessful();
    }
}
