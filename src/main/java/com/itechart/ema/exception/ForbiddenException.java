package com.itechart.ema.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ForbiddenException extends AbstractThrowableProblem {

    public ForbiddenException(final String message) {
        super(ErrorType.FORBIDDEN, "Forbidden", Status.FORBIDDEN, message);
    }

}
