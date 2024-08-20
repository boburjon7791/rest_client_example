package com.rest_client.rest_client_example.controller;

import com.rest_client.rest_client_example.model.common.Header;
import com.rest_client.rest_client_example.model.request.PostReq;
import com.rest_client.rest_client_example.service.PostService;
import com.rest_client.rest_client_example.utils.RestConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RestConstants.basePrefix+"/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public Header<?> create(@RequestBody @Valid Header<PostReq> request){
        return Header.ok(postService.create(request.getData()));
    }

    @GetMapping("/{id}")
    public Header<?> getById(@PathVariable Long id){
        return Header.ok(postService.getById(id));
    }

    @GetMapping
    public Header<?> getAll(){
        return Header.ok(postService.getAll());
    }

    @PutMapping("/{id}")
    public Header<?> update(@RequestBody @Valid Header<PostReq> request, @PathVariable Long id){
        return Header.ok(postService.update(request.getData(), id));
    }

    @DeleteMapping("{id}")
    public Header<?> delete(@PathVariable Long id){
        postService.delete(id);
        return Header.ok();
    }
}
