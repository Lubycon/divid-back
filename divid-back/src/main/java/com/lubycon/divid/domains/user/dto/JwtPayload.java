package com.lubycon.divid.domains.user.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class JwtPayload {
    @NotNull
    private long id;
    @NotNull
    private String issuer;
}
