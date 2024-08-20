package com.rest_client.rest_client_example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GeneralApiException extends RuntimeException{
    private HttpStatus status=HttpStatus.CONFLICT;
    public GeneralApiException(String message){
        super(message);
    }
    public GeneralApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
