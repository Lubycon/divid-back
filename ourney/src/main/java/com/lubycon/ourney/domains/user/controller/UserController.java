package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.service.UserService;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @ApiOperation("테스트 용 -- 추후 삭제")
    @GetMapping("/login/oauth")
    public String kakaoCallback(String code) {
        return code;
    }

    @ApiOperation("카카오로 계속하기")
    @GetMapping(value="/login")
    public LoginRequest login(@RequestHeader("accessToken") String accessToken) {
        LoginRequest userInfo = userService.getUserInfo(accessToken);
        return userInfo;
    }

    @ApiOperation("USER 저장")
    @PostMapping(value="/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse response = userService.login(request);
        userService.updateToken(response);
        return ResponseEntity.ok().body(response);
    }
}
