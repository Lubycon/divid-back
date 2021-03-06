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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;
    @ApiOperation("로그인/회원가입")
    @PostMapping(value = "/googleLogin")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        TokenResponse response;
        String message;
        if (userService.getUserInfo(request)) {
            response = userService.login(request);
            message = ResponseMessages.SUCCESS_LOGIN;
        } else {
            response = userService.signUp(request);
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
