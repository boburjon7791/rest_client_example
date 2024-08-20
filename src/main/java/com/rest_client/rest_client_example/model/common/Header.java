package com.rest_client.rest_client_example.model.common;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Header<T> {

    @NotNull
    private LocalDateTime transactionTime;

    private String resultCode;

    private String resultMsg;

    @Valid
    private T data;

    public static <T> Header<T> ok() {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("0000")
                .resultMsg("OK")
                .build();
    }

    public static <T> Header<T> ok(T data) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("0000")
                .resultMsg("OK")
                .data(data)
                .build();
    }

    public static <T> Header<T> error() {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("E001")
                .resultMsg("ERROR")
                .build();
    }

    public static <T> Header<T> error(String msg) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("E001")
                .resultMsg(msg)
                .build();
    }

    public static <T> Header<T> error(String msg, T data, ErrorCodes resultCode) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("E001")
                .data(data)
                .resultMsg(msg)
                .resultCode(resultCode.getResultCode())
                .build();
    }

    public static <T> Header<T> error(ErrorCodes errorCode, String msg) {
        return Header.<T>builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(errorCode.getResultCode())
                .resultMsg(msg)
                .build();
    }
}
