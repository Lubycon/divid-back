package com.lubycon.ourney.domains.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
public class TokenResponse {
    @NotNull
    private Long userId;
    @NotNull
    private String accessToken;
    @NotNull
    private String refreshToken;
}
