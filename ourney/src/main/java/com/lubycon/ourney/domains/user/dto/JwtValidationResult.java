package com.lubycon.ourney.domains.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
public class JwtValidationResult {
    @NotNull
    private Long id;
    @NotNull
    private boolean validation;
}
