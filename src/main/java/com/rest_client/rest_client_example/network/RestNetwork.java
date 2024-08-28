package com.rest_client.rest_client_example.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest_client.rest_client_example.network.interceptors.HeaderInterceptor;
import com.rest_client.rest_client_example.network.interceptors.LoggingInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RestNetwork {

    private static RestClient restClient;

    private final List<ClientHttpRequestInterceptor> clientHttpRequestInterceptors;

    @PostConstruct
    public void init(){
        restClient=RestClient.builder()
                .requestInterceptors(interceptors -> interceptors.addAll(clientHttpRequestInterceptors)).build();
    }
    private static final ObjectMapper objectMapper=new ObjectMapper();

    public static String get(String apiUrl, Map<String, String> params){
        return restClient.get()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .retrieve()
                .body(String.class);
    }

    /*
    * POST requests
    * */
    public static <T> String post(String apiUrl, T body, MediaType mediaType){
        return restClient.post()
                .uri(apiUrl)
                .body(body)
                .contentType(mediaType)
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

    /*
    * PUT request
    * */
    public static <T> String put(String apiUrl, T body, MediaType mediaType){
        return restClient.put()
                .uri(apiUrl)
                .body(body)
                .contentType(mediaType)
                .retrieve()
                .body(String.class);
    }

    /*
    * DELETE request
    * */
    public static void delete(String apiUrl, Map<String, String> params){
         restClient.delete()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .retrieve()
                .toBodilessEntity();
    }

    /*
    * helper methods
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
