package com.lubycon.ourney.domains.trip.exception;

import javax.persistence.EntityNotFoundException;

public class TripNotFoundException extends EntityNotFoundException {
    public TripNotFoundException(final String message) {
        super(message);
    }
}
