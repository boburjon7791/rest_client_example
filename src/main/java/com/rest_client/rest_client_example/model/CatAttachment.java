package com.rest_client.rest_client_example.model;

public record CatAttachment(
        String id,
        String url,
        Integer with,
        Integer height,
        String original_filename,
        Integer pending,
        Integer approved
) {}
