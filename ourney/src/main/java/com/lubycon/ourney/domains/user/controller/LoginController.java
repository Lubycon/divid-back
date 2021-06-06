package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.domains.user.dto.JwtResponse;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.service.JwtService;
import com.lubycon.ourney.domains.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation("로그인/회원가입")
    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@RequestHeader("accessToken") String accessToken) {
        LoginRequest userInfo = userService.getUserInfo(accessToken);
        TokenResponse response;
        String message;
        if (userInfo.isMember()) {
            response = userService.login(userInfo);
            message = ResponseMessages.SUCCESS_LOGIN;
        } else {
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
