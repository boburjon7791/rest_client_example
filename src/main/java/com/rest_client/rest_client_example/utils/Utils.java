package com.rest_client.rest_client_example.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.util.function.Predicate;

@Slf4j
public class Utils {

    /**
     * this is for handling third party API fail responses
     * */
    public static RestClient.ResponseSpec.ErrorHandler globalErrorHandler = (request, response) -> {
        log.error("Error was occurred : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
        throw new RuntimeException(response.getStatusText());
    };

    /**
     * this is for handling third party API success responses
     * */
    public static RestClient.ResponseSpec.ErrorHandler globalSuccessHandler = (request, response) -> {
        log.info("Called : request method = {},  request uri = {},  response status code = {}", request.getMethod(),request.getURI(), response.getStatusCode());
    };

    /**
     * this is for checking ok status error case
     * */
    public static Predicate<HttpStatusCode> okErrorChecker = httpStatusCode -> !httpStatusCode.is2xxSuccessful();

    /**
     * this is for checking ok status success case
     * */
    public static Predicate<HttpStatusCode> okSuccessChecker = HttpStatusCode::is2xxSuccessful;
}
