package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.model.Post;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/test-headers")
    public Map<?, ?> testHeadersGet(@RequestHeader Map<String, String> headers){
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return headers;
    }

    @PostMapping("/test-headers")
    public Map<?, ?> testHeadersPost(@RequestHeader Map<String, String> headers){
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return headers;
    }

    @PutMapping("/test-headers")
    public Map<?, ?> testHeadersPut(@RequestHeader Map<String, String> headers){
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return headers;
    }

    @DeleteMapping("/test-headers")
    public Map<?, ?> testHeadersDelete(@RequestHeader Map<String, String> headers){
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return headers;
    }

    @GetMapping(value = "/test-multipart-headers", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> testMultipartHeaders(@RequestHeader Map<String, String> headers) throws IOException, IOException {
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return ResponseEntity.ok(Files.readAllBytes(Path.of("pom.xml")));
    }

    @PostMapping(value = "/test-multi-value-map", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Post testMultiValueMapPost(@ModelAttribute Post post, @RequestHeader Map<String, Object> headers){
        System.out.println("post = " + post);
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return post;
    }

    @PutMapping(value = "/test-multi-value-map", consumes =
            MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Post testMultiValueMapPut(@ModelAttribute Post post, @RequestHeader Map<String, Object> headers){
        System.out.println("post = " + post);
        headers.forEach((name, value) -> System.out.println(name + " : " + value));
        return post;
    }
}
