package com.lubycon.ourney.domains.user.service;

import com.lubycon.ourney.domains.user.dto.JwtValidationResult;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.Duration;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.secret}")
    private String SECRET;

    @Autowired
    private UserService userService;

    private final Long accessExpiredTime= Duration.ofHours(1).toMillis();
    private final Long refreshExpiredTime = Duration.ofDays(30).toMillis();

    private JwtUtil jwtUtil;

    @PostConstruct
    public void postConstruct() {
        jwtUtil = new JwtUtil(ISSUER, SECRET);
    }

    public TokenResponse issue(long userId) {
        String accessToken = jwtUtil.createToken(userId, accessExpiredTime);
        String refreshToken = jwtUtil.createToken(userId, refreshExpiredTime);
        userService.updateToken(userId, refreshToken);
        return new TokenResponse(userId, accessToken, refreshToken);
    }


    public JwtValidationResult validate(String token) {
        Jws<Claims> jws;
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET));
        JwtValidationResult result;
        try {
            JwtParserBuilder jpb = Jwts.parserBuilder();
            jpb.setSigningKey(secretKey);
            jws = jpb.build().parseClaimsJws(token);
            result = new JwtValidationResult(Long.parseLong(jws.getBody().get("id").toString()), true);
        }catch(JwtException e) {
            result = new JwtValidationResult(null, true);
        }
        return result;
    }

    public String decode(String token) {
        return jwtUtil.decode(token);
    }
}
