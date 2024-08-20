package com.rest_client.rest_client_example.utils;

import com.rest_client.rest_client_example.exception.GeneralApiException;
import com.rest_client.rest_client_example.model.common.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class Utils {

    /**
     * this is for handling third party API fail responses
     * */
    public static RestClient.ResponseSpec.ErrorHandler globalErrorHandler = (request, response) -> {
        int statusCode = response.getStatusCode().value();
        log.error("Error was occurred : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
        throw new GeneralApiException(response.getStatusText(), HttpStatus.valueOf(statusCode));
    };

    /**
     * this is for handling third party API success responses
     * */
    public static RestClient.ResponseSpec.ErrorHandler globalSuccessHandler = (request, response) -> {
        log.info("Called : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
    };

    /**
     * this is for checking created status error case
     * */
    public static Predicate<HttpStatusCode> createdErrorChecker = httpStatusCode -> httpStatusCode.value() != 201;

    /**
     * this is for checking created status success case
     * */
    public static Predicate<HttpStatusCode> createdSuccessChecker = httpStatusCode -> httpStatusCode.value() == 201;

    /**
     * this is for checking ok status error case
     * */
    public static Predicate<HttpStatusCode> okErrorChecker = httpStatusCode -> httpStatusCode.value() != 200;

    /**
     * this is for checking ok status success case
     * */
    public static Predicate<HttpStatusCode> okSuccessChecker = httpStatusCode -> httpStatusCode.value() == 200;

    /**
     * this is for making validation exception data more readable
     */
    public static  List<?> validationErrorResponseBuilder(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors().stream()
                .map(fieldError -> ValidationErrorResponse.of(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
    }
}
