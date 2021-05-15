package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.common.Constants;
import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.common.exception.SimpleSuccessResponse;
import com.lubycon.ourney.domains.user.dto.LoginRequest;
import com.lubycon.ourney.domains.user.dto.MypageRequest;
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

    @ApiOperation("마이페이지 조회")
    @GetMapping("/mypage")
    public ResponseEntity<MypageRequest> getUser(@LoginId long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @ApiOperation("마이페이지 수정")
    @PutMapping("/mypage")
    public ResponseEntity<SimpleSuccessResponse> updateUser(
            @LoginId long id,
            @RequestBody MypageRequest mypageRequest
    ) {
        userService.updateUser(id, mypageRequest);
        return ResponseEntity.ok().body(new SimpleSuccessResponse(ResponseMessages.SUCCESS_UPDATE_USER));
    }

    @ApiOperation("회원 탈퇴")
    @PostMapping("/withdrawal")
    public ResponseEntity<SimpleSuccessResponse> withdrawal(@LoginId long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(new SimpleSuccessResponse(ResponseMessages.SUCCESS_WITHDRAWAL));
    }
}
