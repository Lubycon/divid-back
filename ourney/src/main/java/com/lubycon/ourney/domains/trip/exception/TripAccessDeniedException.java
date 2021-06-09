package com.lubycon.ourney.domains.trip.exception;

import java.nio.file.AccessDeniedException;

public class TripAccessDeniedException extends AccessDeniedException {
    public TripAccessDeniedException(Long target) {
        super(target + "access denied");
    }
}
