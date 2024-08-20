package com.rest_client.rest_client_example.model.common;

public record ValidationErrorResponse(String fieldName, String errorMessage) {
    public static ValidationErrorResponse of(String fieldName, String errorMessage){
        return new ValidationErrorResponse(fieldName, errorMessage);
    }
}
