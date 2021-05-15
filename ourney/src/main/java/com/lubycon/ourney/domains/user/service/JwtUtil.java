package com.lubycon.ourney.domains.user.service;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class JwtUtil {
    private final String ISSUER;
    private final String SECRET;

    JwtUtil(String issuer, String secret) {
        ISSUER = issuer;
        SECRET = secret;
    }

    public String createToken(Long id, Long expiredTime){
        Date ext = new Date(System.currentTimeMillis()); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);

        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("issuer", ISSUER);
        payloads.put("subject", "login");
        payloads.put("id",id);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact(); // 토큰 생성

        return jwt;
    }


    public String decode(String token) {
        String[] splitToken = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(splitToken[1]);
        String decodedString = null;
        try {
            decodedString = new String(decodedBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedString;
    }
}
