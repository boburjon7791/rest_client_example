package com.rest_client.rest_client_example.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostReq(
        @NotBlank
        String title,

        @NotBlank
        String body,

        @NotNull
        Long userId
) {
    public static PostReq of(String title, String body, Long userId){
        return new PostReq(title, body, userId);
    }
}
