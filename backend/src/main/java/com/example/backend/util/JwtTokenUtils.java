package com.example.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.backend.entity.User;

import java.util.Date;

public class JwtTokenUtils {

    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    public static String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create().withAudience(user.getName()).withExpiresAt(date).sign(Algorithm.HMAC256(user.getPassword()));
    }

}
