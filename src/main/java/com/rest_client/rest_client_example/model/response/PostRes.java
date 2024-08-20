package com.rest_client.rest_client_example.model.response;

public record PostRes(
        Long id,
        String title,
        String body,
        Long userId
) {
    public static PostRes of(Long id, String title, String body, Long userId){
        return new PostRes(id, title, body, userId);
    }
}
