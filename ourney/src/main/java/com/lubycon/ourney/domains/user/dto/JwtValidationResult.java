package com.lubycon.ourney.domains.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
@Getter
@AllArgsConstructor
public class JwtValidationResult {
    @NotNull
    Long id;
    @NotNull
    boolean validation;
}
