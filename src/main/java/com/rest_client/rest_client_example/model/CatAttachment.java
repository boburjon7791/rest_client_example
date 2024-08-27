package com.rest_client.rest_client_example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CatAttachment(
        String id,

        String url,

        Integer width,

        Integer height,

        @JsonProperty("original_filename")
        String originalFilename,

        Integer pending,

        Integer approved
) {}
