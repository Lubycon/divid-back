package com.lubycon.divid.domains.user.exception;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(final String message) {
        super(message);
    }
}
