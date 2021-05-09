package com.lubycon.ourney.common.config.interceptor;

import com.google.gson.Gson;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import com.lubycon.ourney.domains.user.dto.JwtPayload;
import com.lubycon.ourney.domains.user.service.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IllegalArgumentException {
        log.info("####### Interceptor preHandle Start!!!");
        String jwtAccessToken = request.getHeader("jwtAccessToken");
        String jwtRefreshToken = request.getHeader("jwtRefreshToken");
        if(jwtRefreshToken == null) {
            if(jwtAccessToken != null && jwtAccessToken.length() > 0) {
                if((Boolean) jwtUtil.validate(jwtAccessToken, 0).get("validation")){
                    response.addHeader("id",jwtUtil.validate(jwtAccessToken, 0).get("id").toString());
                    return true;}
                else throw new IllegalArgumentException("Access Token Error!!!");
            }else {
                throw new IllegalArgumentException("No Access Token!!!");
            }
        }else {
            // AccessToken 만료
            if((Boolean) jwtUtil.validate(jwtRefreshToken, 1).get("validation")) {
                String accessTokenDecode = jwtUtil.decode(jwtRefreshToken);
                Gson gson = new Gson();
                log.info("ACCESS: "+accessTokenDecode);
                JwtPayload jwtPayload = gson.fromJson(accessTokenDecode, JwtPayload.class);

                String refreshTokenInDBMS = userRepository.findRefreshTokenById(jwtPayload.getId());

                if(refreshTokenInDBMS.equals(jwtRefreshToken)) {
                    String newToken = jwtUtil.createToken(jwtPayload.getId());
                    log.info("재발급 완료 ");
                    response.addHeader("accessToken", newToken);
                    response.addHeader("id",jwtUtil.validate(newToken, 0).get("id").toString());
                }else {
                    throw new IllegalArgumentException("Refresh Token Error!!!");
                }
                return true;
            }else {
                throw new IllegalArgumentException("Refresh Token Error!!!");
            }
        }

    }

}

