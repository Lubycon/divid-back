package com.lubycon.ourney.domains.user.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtUtil {
    @Value("${jwt.issuer}")
    private String ISSUER;
    @Value("${jwt.secret}")
    private String SECRET;

    public String createToken(Long id){
        Long expiredTime = 1000 * 60L * 60L; // 토큰 유효 시간 (1시간)

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

    public String createRefreshToken(Long id){
        Long expiredTime = 1000 * 60L * 60L * 24L * 30; // 토큰 유효 시간 (30일)

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

    public Map<String, Object> validate(String token, int type) { // 1: refresh , 0 : access
        log.info("VALIDATION!!!!===>"+token);
        Jws<Claims> jws;
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        Map<String, Object> map = new HashMap<>();
        try {
            JwtParserBuilder jpb = Jwts.parserBuilder();
            jpb.setSigningKey(secretKey);
            jws = jpb.build().parseClaimsJws(token);

           // System.out.println(jws);
            log.info("#####"+jws.getBody());
            map.put("validation",true);
            map.put("id",jws.getBody().get("id"));
        }catch(JwtException e) {
            map.put("validation", false);
        }
        return map;
    }

    public String decode(String token) {
        String[] splitToken = token.split("\\.");
        log.info(splitToken[1]);
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
