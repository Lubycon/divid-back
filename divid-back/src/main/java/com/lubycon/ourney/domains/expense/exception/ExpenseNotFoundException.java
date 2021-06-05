package com.lubycon.ourney.domains.expense.exception;

import javax.persistence.EntityNotFoundException;

public class ExpenseNotFoundException extends EntityNotFoundException {
    public ExpenseNotFoundException(final String message) {
        super(message);
    }
}
