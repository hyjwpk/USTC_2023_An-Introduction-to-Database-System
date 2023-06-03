package com.example.backend.common;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
    private final Integer code;

    public MyException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }
}