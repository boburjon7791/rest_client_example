package com.rest_client.rest_client_example.exception;

import com.rest_client.rest_client_example.model.common.ErrorCodes;
import com.rest_client.rest_client_example.model.common.Header;
import com.rest_client.rest_client_example.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public HttpEntity<?> generalApiExceptionHandler(GeneralApiException exception){
        log.error("General Api Exception was handled : {0}", exception);
        return ResponseEntity.status(exception.getStatus().value())
                    .body(Header.error(exception.toString()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Header<?> exceptionHandler(Exception exception){
        log.error("Exception was handled : {0}", exception);
        return Header.error(ErrorCodes.SERVER_ERROR, exception.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Header<?> validationExceptionHandler(MethodArgumentNotValidException exception){
        log.error("Validation Exception was handler : {0}", exception);
        return Header.error(ErrorCodes.VALIDATION_ERROR.name(), Utils.validationErrorResponseBuilder(exception), ErrorCodes.VALIDATION_ERROR);
    }
}
