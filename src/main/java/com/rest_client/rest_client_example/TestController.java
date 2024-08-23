package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.model.Attachment;
import com.rest_client.rest_client_example.network.RestNetwork;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object save(@RequestParam MultipartFile file){
        Object response;
        String body;

        // for error case
//        body=RestNetwork.saveImage(RestNetwork.IMAGE_UPLOAD+"ddd", file, Attachment.class);

        // for success case
        body=RestNetwork.saveImage(RestNetwork.IMAGE_UPLOAD+"ddd", file, Attachment.class);
        response=RestNetwork.parseFromJson(body, Attachment.class);
        // for error case
//        body=RestNetwork.saveImage(RestNetwork.CAT_IMAGE_UPLOAD+"ddaa", file, CatAttachment.class);

        // for success case
//        body=RestNetwork.saveImage(RestNetwork.CAT_IMAGE_UPLOAD, file, CatAttachment.class);
//        response=RestNetwork.parseFromJson(body, CatAttachment.class);

        System.out.println("body = " + body);

        return response;
    }
}
