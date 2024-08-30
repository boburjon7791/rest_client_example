package com.rest_client.rest_client_example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RestNetwork {

    private static final RestClient restClient=RestClient.builder().requestInterceptor(new SingleInterceptor()).build();

    private static final ObjectMapper objectMapper=new ObjectMapper();

    /**
     * GET requests
     * */
    public static ResponseEntity<String> get1(String apiUrl, Map<String, String> params){

        AtomicReference<HttpStatusCode> statusCode=new AtomicReference<>();

        AtomicReference<HttpHeaders> headers=new AtomicReference<>();

        String responseBody = restClient.get()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .retrieve()
                .onStatus(httpStatusCode -> true, (request, response) -> {
                    statusCode.set(response.getStatusCode());
                    headers.set(response.getHeaders());
                })
                .body(String.class);

        return ResponseEntity.status(statusCode.get()).headers(headers.get()).body(responseBody);
    }

    public static String get2(String apiUrl, Map<String, String> params){
        return restClient.get()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .retrieve()
                .body(String.class);
    }

    /**
    * POST requests
    * */
    public static <T> String post(String apiUrl, T body){
        return restClient.post()
                .uri(apiUrl)
                .body(body)
                .retrieve()
                .body(String.class);
    }

    public static String post(String apiUrl, Map<String, MultipartFile> files){

        MultiValueMap<String, Resource> params = new LinkedMultiValueMap<>();

        files.forEach((name, file) -> params.add(name, file.getResource()));

        return restClient.post()
                .uri(apiUrl)
                .body(params)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .retrieve()
                .body(String.class);
    }

    /**
    * PUT request
    * */
    public static <T> String put(String apiUrl, T body){
        return restClient.put()
                .uri(apiUrl)
                .body(body)
                .retrieve()
                .body(String.class);
    }

    /**
    * DELETE request
    * */
    public static void delete(String apiUrl, Map<String, String> params){
         restClient.delete()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .retrieve()
                .toBodilessEntity();
    }

    /**
    * Helper methods
    * */
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

    public static MultiValueMap<String, String> convertToMultiValueMap(Map<String, String> params){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        params.forEach(multiValueMap::add);

        return multiValueMap;
    }

    /**
     * External APIs
     * */
    public static final String POSTS_API="https://jsonplaceholder.typicode.com/posts";
    public static final String CAT_IMAGE_UPLOAD="https://api.thecatapi.com/v1/images/upload";

    /**
     * External APIs' keys
     * */
    public static final String CAT_API_KEY="live_MCgVsBDdcRjZO3MNuKjaMDlAhrdMO5s2G3DDlJ3qvvrj6EnJdnrcXADToyujNG8L";

    /**
     * External APIs' header names
     * */
    public static final String X_API_KEY="x-api-key";

    /**
     * External APIs' param names
     * */
    public static final String FILE="file";
}
