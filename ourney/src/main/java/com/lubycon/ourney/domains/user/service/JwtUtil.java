package com.lubycon.ourney.domains.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Date;

@RequiredArgsConstructor
@Service
public class JwtUtil {
    private String TEST_SIGN_KEY = "TEST_KEY_SYJ";
    private String ISSUER = "SYJ";

    public String createToken(Long id){
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis()+(1000 * 60 * 60))) // 1시간
                .withClaim("id", id)
                .sign(Algorithm.HMAC256(TEST_SIGN_KEY));
    }

    public String createRefreshToken(Long id){
        return JWT.create()
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(System.currentTimeMillis()+(1000 * 3600 * 24 * 30))) // 한달
                .withClaim("id", id)
                .sign(Algorithm.HMAC256(TEST_SIGN_KEY));
    }

    public void verifyToken(String givenToken){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TEST_SIGN_KEY))
                .withIssuer(ISSUER)
                .build();

        verifier.verify(givenToken);
    }


}
