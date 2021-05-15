package com.lubycon.ourney.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED,"액세스 정보를 확인해 주세요."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;
    private String message;

    ExceptionEnum(HttpStatus status) {
        this.status = status;
    }

    ExceptionEnum(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }
}
