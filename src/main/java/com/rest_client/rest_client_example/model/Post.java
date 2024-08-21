package com.rest_client.rest_client_example.model;

public record Post(
        Long userId,
        Long id,
        String title,
        String body
) {}
