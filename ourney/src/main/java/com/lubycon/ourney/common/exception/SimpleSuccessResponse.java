package com.lubycon.ourney.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SimpleSuccessResponse {
    private final String message;
}

