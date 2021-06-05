package com.lubycon.ourney.domains.user.dto;
import org.jetbrains.annotations.NotNull;
import lombok.Getter;

@Getter
public class JwtPayload {
    @NotNull
    private long id;
    @NotNull
    private String issuer;
}
