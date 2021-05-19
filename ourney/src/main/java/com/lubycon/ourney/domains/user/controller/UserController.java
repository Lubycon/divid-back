package com.lubycon.ourney.domains.user.controller;

import com.lubycon.ourney.common.ResponseMessages;
import com.lubycon.ourney.common.config.interceptor.LoginId;
import com.lubycon.ourney.common.error.SimpleSuccessResponse;
import com.lubycon.ourney.domains.user.dto.UserInfoRequest;
import com.lubycon.ourney.domains.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @ApiOperation("마이페이지 조회")
    @GetMapping("/mypage")
    public ResponseEntity<UserInfoRequest> getUser(@LoginId long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @ApiOperation("마이페이지 수정")
    @PutMapping("/mypage")
    public ResponseEntity<SimpleSuccessResponse> updateUser(
            @LoginId long id,
            @RequestBody UserInfoRequest userInfoRequest
    ) {
        userService.updateUser(id, userInfoRequest);
        return ResponseEntity.ok().body(new SimpleSuccessResponse(ResponseMessages.SUCCESS_UPDATE_USER));
    }

    @ApiOperation("회원 탈퇴")
    @PostMapping("/withdrawal")
    public ResponseEntity<SimpleSuccessResponse> withdrawal(@LoginId long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(new SimpleSuccessResponse(ResponseMessages.SUCCESS_WITHDRAWAL));
    }
}
