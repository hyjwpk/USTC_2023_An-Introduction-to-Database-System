package com.example.backend.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfig {
    @ExceptionHandler(MyException.class)
    public Response handle(MyException e) {
        e.printStackTrace();
        return new Response(e.getCode(), e.getMessage(), null);
    }
}
