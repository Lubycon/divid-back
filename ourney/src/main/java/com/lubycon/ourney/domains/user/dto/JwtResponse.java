package com.lubycon.ourney.domains.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class JwtResponse {
    @NotNull
    private String accessToken;
    @Nullable
    private String refreshToken;
    @Nullable
    private String message;

    @Builder
    public JwtResponse(String accessToken, String refreshToken, String message){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
    }
}
