package com.example.rest_client_example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

public final class RestNetwork {
    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
    private static final RestClient REST_CLIENT= RestClient.builder().requestInterceptor(new CustomRestClientInterceptor()).build();

    /**
     * external APIs
     * */
    public static final String JSON_PLACE_HOLDER="https://jsonplaceholder.typicode.com";
    public static final String POSTS="/posts";

    /**
     * external APIs' headers
     * */
    public static final String API_KEY_NAME="api_key_name";
    public static final String API_KEY_VALUE="api_key_value";

    public static <Response, Request> Response post(String url, Request body, Class<Response> responseType, MediaType mediaType){
        return REST_CLIENT.post()
                .uri(url)
                .contentType(mediaType == null ? MediaType.APPLICATION_JSON : mediaType)
                .body(body)
                .retrieve()
                .body(responseType);
    }

    public static <Response, ID, Request> Response put(String url, Request body, ID id, Class<Response> responseType, MediaType mediaType){
        return REST_CLIENT.put()
                .uri(url, uriBuilder -> uriBuilder.path("/"+id).build())
                .contentType(mediaType == null ? MediaType.APPLICATION_JSON : mediaType)
                .body(body)
                .retrieve()
                .body(responseType);
    }

    public static <Response> Response get(String url, Class<Response> responseType){
        return REST_CLIENT.get()
                .uri(url)
                .retrieve()
                .body(responseType);
    }

    @SneakyThrows
    public static <Response> List<Response> getList(String url, Class<Response> responseType, @Nullable Map<String, String> params){
        String body = REST_CLIENT.get()
                .uri(url, uriBuilder -> {
                    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
                    if (params!=null) {
                        params.forEach((param, value) -> multiValueMap.put(param, List.of(value)));
                    }
                    return uriBuilder.queryParams(multiValueMap).build();
                })
                .retrieve()
                .body(String.class);
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, responseType);
        return OBJECT_MAPPER.readValue(body, collectionType);
    }

    public static <ID> ResponseEntity<Void> delete(String url, ID id){
        return REST_CLIENT.delete()
                .uri(url, uriBuilder -> uriBuilder.path("/"+id).build())
                .retrieve()
                .toBodilessEntity();
    }
}
