package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class JwtTokenResponse {
    @NotNull
    private String JwtAccessToken;
    @Nullable
    private String JwtRefreshToken;
    @Nullable
    private String message;

    @Builder
    public JwtTokenResponse(String JwtAccessToken, String JwtRefreshToken, String message){
        this.JwtAccessToken = JwtAccessToken;
        this.JwtRefreshToken = JwtRefreshToken;
        this.message = message;
    }
}
