package com.lubycon.divid.domains.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class JwtValidationResult {
    @Nullable
    private Long id;
    @NotNull
    private boolean validation;
}
