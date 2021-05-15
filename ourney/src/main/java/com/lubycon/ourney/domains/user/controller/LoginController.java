package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.exception.SimpleSuccessResponse;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import com.lubycon.ourney.domains.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import javassist.Loader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class LoginController {
    private final UserService userService;

    @ApiOperation("로그인/회원가입")
    @PostMapping(value = "/login")
    public ResponseEntity<SimpleSuccessResponse> login(HttpServletResponse res, @RequestHeader("accessToken") String accessToken) {
        LoginRequest userInfo = userService.getUserInfo(accessToken);
        TokenResponse response;
        SimpleSuccessResponse successResponse;
        if (userInfo.isMember()) {
            response = userService.login(userInfo);
            successResponse = new SimpleSuccessResponse(ResponseMessages.SUCCESS_LOGIN);
        } else {
            response = userService.signUp(userInfo);
            successResponse = new SimpleSuccessResponse(ResponseMessages.SUCCESS_SIGN_UP);
        }
        userService.updateToken(response.getUserId(), response.getRefreshToken());
        res.addHeader(Constants.JWT_ACCESS_TOKEN, response.getAccessToken());
        res.addHeader(Constants.JWT_REFRESH_TOKEN, response.getRefreshToken());
        return ResponseEntity.ok().body(successResponse);
    }

}
