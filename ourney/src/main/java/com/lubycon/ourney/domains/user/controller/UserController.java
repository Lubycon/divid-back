package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.common.dto.SimpleSuccessResponse;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.service.JwtUtil;
import com.lubycon.ourney.domains.user.service.UserService;
import com.lubycon.ourney.domains.user.dto.TokenResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @ApiOperation("카카오로 계속하기")
    @GetMapping(value="/login")
    public ResponseEntity<LoginRequest> login(@RequestHeader("accessToken") String accessToken) {
        log.info("카카오 accessToken : "+accessToken);
        LoginRequest userInfo = userService.getUserInfo(accessToken);
        log.info(userInfo.getKakaoId().toString());
        return ResponseEntity.ok().body(userInfo);
    }

    @ApiOperation("USER 저장")
    @PostMapping(value="/login")
    public ResponseEntity<SimpleSuccessResponse> login(@RequestBody LoginRequest request, HttpServletResponse res) {
        TokenResponse response = userService.login(request);
        userService.updateToken(response);
        res.addHeader("jwtAccessToken",response.getAccessToken());
        res.addHeader("jwtRefreshToken",response.getRefreshToken());
        SimpleSuccessResponse successResponse = new SimpleSuccessResponse("회원가입 완료 되었습니다.");
        return ResponseEntity.ok().body(successResponse);
    }

}
