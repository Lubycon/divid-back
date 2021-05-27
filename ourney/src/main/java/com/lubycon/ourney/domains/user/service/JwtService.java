package com.lubycon.ourney.domains.user.service;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.domains.user.dto.JwtValidationResult;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.entity.User;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@RequiredArgsConstructor
@Service
public class JwtService {
    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.secret}")
    private String SECRET;

    private final UserRepository userRepository;

    private final Long accessExpiredTime = Duration.ofHours(1).toMillis();
    private final Long refreshExpiredTime = Duration.ofDays(30).toMillis();

    private JwtUtil jwtUtil;

    @PostConstruct
    public void postConstruct() {
        jwtUtil = new JwtUtil(ISSUER, SECRET);
    }

    public TokenResponse issue(long userId) {
        String accessToken = jwtUtil.createToken(userId, accessExpiredTime);
        String refreshToken = jwtUtil.createToken(userId, refreshExpiredTime);
        updateToken(userId, refreshToken);
        return new TokenResponse(userId, accessToken, refreshToken);
    }

    @Transactional
    public void updateToken(
            long userId,
            @NotNull String refreshToken
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessages.NOT_EXIST_USER + userId));
        user.updateRefreshToken(refreshToken);
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
        } catch (JwtException e) {
            result = new JwtValidationResult(null, true);
        }
        return result;
    }

    public String decode(String token) {
        return jwtUtil.decode(token);
    }
}
