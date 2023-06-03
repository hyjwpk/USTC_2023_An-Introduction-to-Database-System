package com.example.backend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ResponseEnum {

    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    NO_TOKEN(401, "无token，请重新登录"),
    TOKEN_FAIL(402, "token验证失败，请重新登录");

    private final Integer code;
    private final String message;
}