package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.domains.user.dto.JwtResponse;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.service.JwtService;
import com.lubycon.ourney.domains.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
@Slf4j
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ApiOperation("로그인/회원가입")
    @PostMapping(value = "/kakao")
    public ResponseEntity<JwtResponse> login(@RequestHeader("kakaoAccessToken") String accessToken) {
        logger.debug("#######log#######");
        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> login_AccessToken : "+accessToken);
        LoginRequest userInfo = userService.getUserInfo(accessToken);

        TokenResponse response;
        String message;
        if (userInfo.isMember()) {
            logger.debug("#######log_1#######");
            response = userService.login(userInfo);
            message = ResponseMessages.SUCCESS_LOGIN;
        } else {
            logger.debug("#######log_2#######");
            response = userService.signUp(userInfo);
            message = ResponseMessages.SUCCESS_SIGN_UP;
        }
        jwtService.updateToken(response.getUserId(), response.getRefreshToken());
        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(response.getAccessToken())
                .refreshToken(response.getRefreshToken())
                .message(message)
                .build();
        return ResponseEntity.ok().body(jwtResponse);
    }

}
