package com.rest_client.rest_client_example.model.common;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    OK("0000"),

    CONFLICT("E001"),

    SERVER_ERROR("E500"),

    VALIDATION_ERROR("E400");

    final String resultCode;

    ErrorCodes(String resultCode){
        this.resultCode=resultCode;
    }
}
