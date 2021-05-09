package com.lubycon.ourney.domains.user.dto;

import lombok.Getter;

@Getter
public class JwtPayload {
    private Long id;
    private String issuer;
}
