package com.lubycon.ourney.domains.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequest {
    private String email;
    private String name;

    @Builder
    public SignUpRequest(String email, String name){
        this.email = email;
        this.name = name;
    }
}
