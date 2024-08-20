package com.rest_client.rest_client_example.client;

import com.rest_client.rest_client_example.model.request.PostReq;
import com.rest_client.rest_client_example.model.response.PostRes;
import com.rest_client.rest_client_example.utils.RestConstants;
import com.rest_client.rest_client_example.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostClient {
    private final RestClient restClient;

    public PostRes createPost(PostReq post){
        return restClient.post()
                .uri(RestConstants.postsBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(post)
                .retrieve()
                .onStatus(Utils.createdSuccessChecker, Utils.globalSuccessHandler)
                .onStatus(Utils.createdErrorChecker, Utils.globalErrorHandler)
                .body(PostRes.class);
    }

    public PostRes updatePost(PostReq post, Long id){
        return restClient.put()
                .uri(RestConstants.postsBaseUrl+"/"+id)
                .body(post)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .body(PostRes.class);
    }

    public PostRes getById(Long id){
        return restClient.get()
                .uri(RestConstants.postsBaseUrl+"/"+id)
                .retrieve()
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .body(PostRes.class);
    }

    public List<PostRes> getAll(){
        return restClient.get()
                .uri(RestConstants.postsBaseUrl)
                .retrieve()
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .body(List.class);
    }
    public void deleteById(Long id){
        restClient.delete()
                .uri(RestConstants.postsBaseUrl+"/"+id)
                .retrieve()
                .onStatus(Utils.okSuccessChecker, Utils.globalSuccessHandler)
                .onStatus(Utils.okErrorChecker, Utils.globalErrorHandler)
                .toBodilessEntity();
    }
}
