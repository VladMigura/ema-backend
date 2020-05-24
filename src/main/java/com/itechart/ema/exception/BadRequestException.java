package com.itechart.ema.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestException extends AbstractThrowableProblem {

    public BadRequestException(final String message) {
        super(ErrorType.BAD_REQUEST, "Bad Request", Status.BAD_REQUEST, message);
    }

}
