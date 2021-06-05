package com.lubycon.divid.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SimpleSuccessResponse {
    private final String message;
}

