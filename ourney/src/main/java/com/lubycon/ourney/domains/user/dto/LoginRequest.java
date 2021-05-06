package com.lubycon.ourney.domains.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {
    private Long kakaoId;
    private String email;
    private String name;
    private String profileImg;
    @Builder
    public LoginRequest(Long kakaoId, String email, String name, String profileImg){
        this.kakaoId = kakaoId;
        this.email = email;
        this.name = name;
        this.profileImg = profileImg;
    }
}
