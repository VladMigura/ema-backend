package com.itechart.ema.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ConflictException extends AbstractThrowableProblem {

    public ConflictException(final String message) {
        super(ErrorType.CONFLICT, "Conflict", Status.CONFLICT, message);
    }
}
