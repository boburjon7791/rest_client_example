package com.example.rest_client_example.model;

public record Post(long id, long userId, String title, String body) {
    public static Post of(long id, long userId, String title, String body){
        return new Post(id, userId, title, body);
    }
}
