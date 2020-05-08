package com.itechart.ema.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NotFoundException extends AbstractThrowableProblem {

    public NotFoundException(final String message) {
        super(ErrorType.NOT_FOUND, "Not Found", Status.NOT_FOUND, message);
    }

}
