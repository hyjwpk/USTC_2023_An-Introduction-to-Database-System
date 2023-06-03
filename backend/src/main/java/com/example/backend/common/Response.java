package com.example.backend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {
    private final Integer code;
    private final String message;
    private final Object data;

    public Response(ResponseEnum responseEnum, Object data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public static Response success(Object data) {
        return new Response(ResponseEnum.SUCCESS, data);
    }

    public static Response success() {
        return new Response(ResponseEnum.SUCCESS, null);
    }

    public static Response fail() {
        return new Response(ResponseEnum.FAIL, null);
    }

    public static Response noToken() {
        return new Response(ResponseEnum.NO_TOKEN, null);
    }

    public static Response tokenFail() {
        return new Response(ResponseEnum.TOKEN_FAIL, null);
    }
}
