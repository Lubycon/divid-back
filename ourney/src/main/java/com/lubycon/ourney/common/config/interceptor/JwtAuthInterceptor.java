package com.lubycon.ourney.common.config.interceptor;

import com.google.gson.Gson;
import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.domains.user.dto.JwtValidationResult;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.entity.UserRepository;
import com.lubycon.ourney.domains.user.dto.JwtPayload;
import com.lubycon.ourney.domains.user.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IllegalArgumentException {
        String jwtAccessToken = request.getHeader(Constants.JWT_ACCESS_TOKEN);
        String jwtRefreshToken = request.getHeader(Constants.JWT_REFRESH_TOKEN);
        if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
            return true;
        }

        // 액세스 토큰이 만료되지 않아, 액세스 토큰만 보내는 경우
        if (!Objects.isNull(jwtAccessToken) && Objects.isNull(jwtRefreshToken)) {
            JwtValidationResult validateResult = jwtService.validate(jwtAccessToken);
            if(validateResult.isValidation()){
                request.setAttribute(Constants.HEADER_ID, validateResult.getId().toString());
            }else {
                throw new IllegalArgumentException(ResponseMessages.TOKEN_ERROR);
            }
        } else {
            JwtValidationResult validateResult = jwtService.validate(jwtRefreshToken);
            if(validateResult.isValidation()) {
                reissueToken(response,jwtRefreshToken);
                request.setAttribute(Constants.HEADER_ID, validateResult.getId().toString());
            } else {
                throw new IllegalArgumentException(ResponseMessages.TOKEN_ERROR);
            }
        }
        return true;
    }

    public void reissueToken(HttpServletResponse response, String jwtRefreshToken){
        String accessTokenDecode = jwtService.decode(jwtRefreshToken);
        Gson gson = new Gson();
        JwtPayload jwtPayload = gson.fromJson(accessTokenDecode, JwtPayload.class);
        String refreshTokenInDBMS = userRepository.findRefreshTokenById(jwtPayload.getId());
        if(refreshTokenInDBMS.equals(jwtRefreshToken)) {
            TokenResponse tokenResponse = jwtService.issue(jwtPayload.getId());
            response.addHeader(Constants.JWT_ACCESS_TOKEN, tokenResponse.getAccessToken());
            response.addHeader(Constants.JWT_REFRESH_TOKEN, tokenResponse.getRefreshToken());
        }else {
            throw new IllegalArgumentException(ResponseMessages.TOKEN_ERROR);
        }
    }
}
