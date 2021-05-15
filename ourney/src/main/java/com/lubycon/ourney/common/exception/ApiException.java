package com.lubycon.ourney.common.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final ExceptionEnum error;

    public ApiException(ExceptionEnum e){
        super(e.getMessage());
        this.error = e;
    }
}
