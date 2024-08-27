package com.rest_client.rest_client_example.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RestNetwork {
    private static final RestClient restClient=RestClient.builder().requestInterceptor(new RestClientInterceptor()).build();
    private static final ObjectMapper objectMapper=new ObjectMapper();

    public static String get(String apiUrl, Map<String, String> params, HttpHeaders headers){
        return restClient.get()
                .uri(apiUrl, uriBuilder -> uriBuilder.replaceQueryParams(convertToMultiValueMap(params)).build())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .body(String.class);
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
                .body(String.class);
    }

    public static String post(String apiUrl, MultipartFile file, HttpHeaders headers){
        Resource resource = file.getResource();

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);

        headers.set("x-api-key", RestNetwork.CAT_API_KEY);

        return restClient.post()
                .uri(apiUrl)
                .body(param)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
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

    public static HttpHeaders emptyHeaders(){
        return new HttpHeaders();
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
    public static final String IMAGE_URL="https://media.wired.com/photos/64daad6b4a854832b16fd3bc/master/w_1920,c_limit/How-to-Choose-a-Laptop-August-2023-Gear.jpg";
    public static final String IMAGE_URL2="https://i.pinimg.com/originals/72/22/78/722278c489e2563abc3e0aa91901bb16.jpg";
    public static final String IMAGE_UPLOAD="https://v2.convertapi.com/upload";
    public static final String CAT_IMAGE_UPLOAD="https://api.thecatapi.com/v1/images/upload";

    /**
     * External APIs' keys
     * */
    public static final String CAT_API_KEY="live_MCgVsBDdcRjZO3MNuKjaMDlAhrdMO5s2G3DDlJ3qvvrj6EnJdnrcXADToyujNG8L";
}
