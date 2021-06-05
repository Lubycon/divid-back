package com.lubycon.divid.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    ENTITY_NOT_FOUND("Entity Not Found"),
    INVALID_VALUE_ERROR("Invalid Value"),
    INTERNAL_SERVER_ERROR("Server Error"),
    HANDLE_ACCESS_DENIED("Access is Denied"),
    ;

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}