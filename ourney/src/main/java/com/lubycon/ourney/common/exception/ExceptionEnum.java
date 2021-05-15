package com.lubycon.ourney.common.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
@Getter
@ToString
public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "400"),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED,"401","액세스 정보를 확인해 주세요."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "403","권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
