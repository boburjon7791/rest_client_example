package com.rest_client.rest_client_example.service;

import com.rest_client.rest_client_example.client.PostClient;
import com.rest_client.rest_client_example.model.request.PostReq;
import com.rest_client.rest_client_example.model.response.PostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostClient postClient;

    public PostRes create(PostReq post){
        return postClient.createPost(post);
    }

    public PostRes getById(Long id){
        return postClient.getById(id);
    }

    public List<PostRes> getAll(){
        return postClient.getAll();
    }

    public PostRes update(PostReq post, Long id){
        return postClient.updatePost(post, id);
    }

    public void delete(Long id){
        postClient.deleteById(id);
    }
}
