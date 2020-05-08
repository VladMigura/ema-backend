package com.itechart.ema.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

public class BadRequestException extends AbstractThrowableProblem {

    public BadRequestException(final String message) {
        super(ErrorType.BAD_REQUEST, "Bad Request", Status.BAD_REQUEST, message);
    }

    public BadRequestException(final String message, final ThrowableProblem exception) {
        super(ErrorType.BAD_REQUEST, "Bad Request", Status.BAD_REQUEST, message, null, exception);
    }

}
