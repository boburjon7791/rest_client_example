package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.model.CatAttachment;
import com.rest_client.rest_client_example.network.RestNetwork;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class TestController {
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object save(@RequestParam MultipartFile file){
        Object response;
        String body;

        // for error case
//        body=RestNetwork.post(RestNetwork.CAT_IMAGE_UPLOAD+"ddd", file);

        // for success case
        body=RestNetwork.post(RestNetwork.CAT_IMAGE_UPLOAD, Map.of(RestNetwork.FILE, file));

        response=RestNetwork.parseFromJson(body, CatAttachment.class);

        System.out.println("body = " + body);

        return response;
    }
}
